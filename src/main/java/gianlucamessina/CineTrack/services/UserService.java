package gianlucamessina.CineTrack.services;

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

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder bCrypt;

    public User findById(UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException(userId));
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()->new NotFoundException(email));
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
}
