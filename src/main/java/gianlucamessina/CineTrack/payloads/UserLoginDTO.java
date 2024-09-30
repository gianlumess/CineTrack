package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(@NotEmpty(message = "L'email è obbligatoria!")
                           @Email(message = "formato email non valido--- example@example.com")
                           String email,
                           @NotEmpty(message = "La password è obbligatoria!")
                           @Size(min = 4,message = "la password deve contenere almeno 4 caratteri!")
                           String password) {
}
