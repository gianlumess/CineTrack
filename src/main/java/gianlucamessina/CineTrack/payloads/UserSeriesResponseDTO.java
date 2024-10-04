package gianlucamessina.CineTrack.payloads;

import gianlucamessina.CineTrack.enums.ShowStatus;

import java.time.LocalDate;
import java.util.UUID;

public record UserSeriesResponseDTO(UUID id,
                                    ShowStatus showStatus,
                                    UUID userId,
                                    long seriesId,
                                    LocalDate dateAddedToList) {
}
