package gianlucamessina.CineTrack.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewCommentDTO(@NotEmpty(message = "Devi inserire un tuo commento sullo show!")
                            String content) {
}
