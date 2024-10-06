package gianlucamessina.CineTrack.payloads;

import java.time.LocalDate;
import java.util.UUID;

public record CommentResponseDTO(UUID id,
                                 String content,
                                 long showId,
                                 UUID userId,
                                 LocalDate dateComment) {
}
