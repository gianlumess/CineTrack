package gianlucamessina.CineTrack.payloads;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(UUID id,
                              String name,
                              String surname,
                              String username,
                              String email,
                              String avatar,
                              LocalDate creationDate) {
}
