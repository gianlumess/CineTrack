package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewRatingDTO(@NotNull(message = "Il voto è obbligatorio!")
                           @Size(min = 1, max = 5, message = "il voto deve essere compreso da 1 a 5!")
                           int rating,
                           @NotNull(message = "devi inserire l'id dello show da valutare(movie/series)!")
                           long showId) {
}
