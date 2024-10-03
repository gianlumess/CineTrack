package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NewUserMovieDTO(@NotEmpty(message = "Lo stato del film è obbligatorio!")
                              @Pattern(regexp = "TO_WATCH|WATCHED",
                                      message = "Lo stato del film deve essere  TO_WATCH oppure WATCHED")
                              String showStatus,
                              @NotNull(message = "L'ID del film è obbligatorio!")
                              long movieId) {
}
