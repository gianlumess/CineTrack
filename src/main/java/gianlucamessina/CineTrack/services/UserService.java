package gianlucamessina.CineTrack.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.enums.Role;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.payloads.NewUserDTO;
import gianlucamessina.CineTrack.payloads.UserResponseDTO;
import gianlucamessina.CineTrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder bCrypt;
    @Autowired
    private Cloudinary cloudinary;

    public User findById(UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException(userId));
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Non è stato trovato alcun utente con la email: "+email));
    }

    //FIND ALL CON PAGINAZIONE
    public Page<UserResponseDTO> findAll(int page, int size, String SortBy){
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(SortBy));
        Page<User>userPage=this.userRepository.findAll(pageable);

        return userPage.map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatar(),
                user.getCreationDate()
        ));
    }


    //SAVE DI UN NUOVO USER
    public UserResponseDTO save(NewUserDTO body){
        this.userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw  new BadRequestException("L'email: "+body.email()+" è già in uso!");
        });

        this.userRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("L'username: "+body.username()+" è già in uso!");
        });

        User newUser=new User(body.name(), body.surname(), body.username(), body.email(), bCrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name="+body.name()+"+"+body.surname(), Role.USER);
        this.userRepository.save(newUser);
        return new UserResponseDTO(newUser.getId(),newUser.getName(), newUser.getSurname(), newUser.getUsername(),
                newUser.getEmail(), newUser.getAvatar(), newUser.getCreationDate());
    }

    //FIND BY ID AND UPDATE
    public UserResponseDTO findByIdAndUpdate(UUID userId,NewUserDTO body){
        User found=this.findById(userId);
        //controllo prima di modificare l'user se email e username sono già utilizzate
        if (!Objects.equals(found.getEmail(), body.email())&& !Objects.equals(found.getUsername(), body.username())){

        this.userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw  new BadRequestException("L'email: "+body.email()+" è già in uso!");
        });
        this.userRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("L'username: "+body.username()+" è già in uso!");
        });
        }

        found.setName(body.name());
        found.setSurname(body.surname());
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(bCrypt.encode(body.password()));

        this.userRepository.save(found);
        return new UserResponseDTO(found.getId(), found.getName(), found.getSurname(), found.getUsername(),
                found.getEmail(), found.getAvatar(), found.getCreationDate());

    }

    //DELETE
    public void findByIdAndDelete(UUID userId){
        User found=this.findById(userId);
        this.userRepository.delete(found);
    }

    //EDIT AVATAR
    public UserResponseDTO editAvatarPic(UUID userId, MultipartFile pic) throws IOException {
        User found=this.findById(userId);

        String url= (String) cloudinary.uploader().upload(pic.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL : "+url);

        found.setAvatar(url);
        this.userRepository.save(found);
        return new UserResponseDTO(found.getId(), found.getName(), found.getSurname(), found.getUsername(),
                found.getEmail(), found.getAvatar(), found.getCreationDate());
    }
}
