package gianlucamessina.CineTrack.payloads;

import java.time.LocalDate;
import java.util.UUID;

public record RatingResponseDTO(UUID id,
                                int rating,
                                long showId,
                                UUID userId,
                                LocalDate dateRating) {
}
