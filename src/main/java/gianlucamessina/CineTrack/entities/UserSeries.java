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
@Table(name = "user_series")
public class UserSeries {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private long seriesId;
    private LocalDate dateAddedToList;

    public UserSeries(ShowStatus showStatus, User user, long seriesId) {
        this.showStatus = showStatus;
        this.user = user;
        this.seriesId = seriesId;
        this.dateAddedToList = LocalDate.now();
    }
}
