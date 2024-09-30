package gianlucamessina.CineTrack.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String content;
    private long showId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate dateComment;

    public Comment(String content, long showId, User user) {
        this.content = content;
        this.showId = showId;
        this.user = user;
        this.dateComment = LocalDate.now();
    }
}
