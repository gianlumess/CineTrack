package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewRatingDTO(@NotNull(message = "Il voto è obbligatorio!")
                           @Min(value = 1, message = "La valutazione minima è 1")
                           @Max(value = 5, message = "La valutazione massima è 5")
                           int rating) {
}
