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
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int rating;
    private long showId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate dateRating;

    public Rating(int rating, long showId, User user) {
        this.rating = rating;
        this.showId = showId;
        this.user = user;
        this.dateRating = LocalDate.now();
    }
}
