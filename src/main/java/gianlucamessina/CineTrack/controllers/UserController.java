package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.NewUserDTO;
import gianlucamessina.CineTrack.payloads.UserResponseDTO;
import gianlucamessina.CineTrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //GET LISTA DI USER
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserResponseDTO> findAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "15") int size,
                                         @RequestParam(defaultValue = "id") String sortBy){
        return this.userService.findAll(page,size,sortBy);
    }

    //GET FIND BY ID
    @GetMapping("/{userId}")
    public UserResponseDTO findById(@PathVariable UUID userId){
        User found=this.userService.findById(userId);
        return new UserResponseDTO(found.getId(), found.getName(), found.getSurname(), found.getUsername(),
                found.getEmail(), found.getAvatar(), found.getCreationDate());
    }

    //FIND BY ID AND UPDATE
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO findByIdAndUpdate(@PathVariable UUID userId, @RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: "+messages);
        }

        return this.userService.findByIdAndUpdate(userId,body);
    }

    //DELETE
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID userId){
        this.userService.findByIdAndDelete(userId);
    }

    //UPDATE AVATAR PIC
    @PatchMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponseDTO editAvatarPic(@PathVariable UUID userId, @RequestParam("pic")MultipartFile pic) throws IOException {
        return this.userService.editAvatarPic(userId,pic);
    }

    //ENDPOINTS ACCESSIBILI ESCLUSIVAMENTE APPARTENENTI A CHI EFFETTUA LA RICHIESTA
    @GetMapping("/me")
    public UserResponseDTO getMyProfile(@AuthenticationPrincipal User user){
        return new UserResponseDTO(user.getId(), user.getName(), user.getSurname(), user.getUsername(),
                user.getEmail(), user.getAvatar(), user.getCreationDate());
    }

    @PutMapping("/me")
    public UserResponseDTO updateMyProfile(@AuthenticationPrincipal User user,@RequestBody @Validated NewUserDTO body){
        return this.userService.findByIdAndUpdate(user.getId(),body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyProfile(@AuthenticationPrincipal User user){
        this.userService.findByIdAndDelete(user.getId());
    }

    @PatchMapping("/me")
    public UserResponseDTO editMyAvatar(@AuthenticationPrincipal User user,@RequestParam("pic")MultipartFile pic) throws IOException {
        return this.userService.editAvatarPic(user.getId(),pic);
    }
}
