package gianlucamessina.CineTrack.services;


import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.UnauthorizedException;
import gianlucamessina.CineTrack.payloads.UserLoginDTO;
import gianlucamessina.CineTrack.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    PasswordEncoder bCrypt;

    public String controlloCredenzialiEdGenerazioneToken(UserLoginDTO body){
        //controllo tramite email se l'utente esiste
        User found=this.userService.findByEmail(body.email());
        if(bCrypt.matches(body.password(), found.getPassword())){
            return jwtTools.createToken(found);
        }else {
            throw  new UnauthorizedException("Credenziali sbagliate!");
        }
    }


}
