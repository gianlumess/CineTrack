package gianlucamessina.CineTrack.payloads;

import gianlucamessina.CineTrack.enums.ShowStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UserMovieResponseDTO(UUID id,
                                   ShowStatus showStatus,
                                   UUID userId,
                                   long movieId,
                                   LocalDate dateAddedToList) {
}
