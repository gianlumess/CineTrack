package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record EditShowStatusDTO(@NotEmpty(message = "Lo stato del film è obbligatorio!")
                                @Pattern(regexp = "TO_WATCH|WATCHED",
                                        message = "Lo stato del film deve essere  TO_WATCH oppure WATCHED")
                                String showStatus) {
}
