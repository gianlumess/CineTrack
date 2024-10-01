package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.NewUserDTO;
import gianlucamessina.CineTrack.payloads.UserLoginDTO;
import gianlucamessina.CineTrack.payloads.UserLoginRespDTO;
import gianlucamessina.CineTrack.services.AuthService;
import gianlucamessina.CineTrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authorization")
    public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody @Validated UserLoginDTO payload){
        return new UserLoginRespDTO(this.authService.controlloCredenzialiEdGenerazioneToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO payload, BindingResult validation){
        if(validation.hasErrors()){
            String messages=validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: "+messages);
        }

        return this.userService.save(payload);
    }

}
