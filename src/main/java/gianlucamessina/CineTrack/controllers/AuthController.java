package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.payloads.UserLoginDTO;
import gianlucamessina.CineTrack.payloads.UserLoginRespDTO;
import gianlucamessina.CineTrack.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
    public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody @Validated UserLoginDTO payload){
        return new UserLoginRespDTO(this.authService.controlloCredenzialiEdGenerazioneToken(payload));
    }



}
