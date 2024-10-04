package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    // Trova la valutazione di un utente per uno specifico film/serie
    Optional<Rating> findByUserIdAndShowId(UUID userId, long showId);

    // Trova tutte le valutazioni di un utente specifico
    List<Rating> findByUserId(UUID userId);
}
