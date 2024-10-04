package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NewUserSeriesDTO(@NotEmpty(message = "Lo stato della serie è obbligatorio!")
                               @Pattern(regexp = "TO_WATCH|WATCHED",
                                       message = "Lo stato della serie deve essere  TO_WATCH oppure WATCHED")
                               String showStatus,
                               @NotNull(message = "L'ID della serie è obbligatorio!")
                               long seriesId) {
}
