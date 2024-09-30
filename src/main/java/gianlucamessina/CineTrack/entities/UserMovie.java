package gianlucamessina.CineTrack.entities;

import gianlucamessina.CineTrack.enums.ShowStatus;
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
@Table(name = "user_movies")
public class UserMovie {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long movieId;
    private LocalDate dateAddedToList;

    public UserMovie(ShowStatus showStatus, User user, long movieId) {
        this.showStatus = showStatus;
        this.user = user;
        this.movieId = movieId;
        this.dateAddedToList = LocalDate.now();
    }
}
