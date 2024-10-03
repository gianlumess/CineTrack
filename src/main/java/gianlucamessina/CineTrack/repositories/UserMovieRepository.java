package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.UserMovie;
import gianlucamessina.CineTrack.enums.ShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, UUID> {

    //TROVA TUTTI I FILM DI UN UTENTE
    List<UserMovie> findByUserId(UUID userId);

    // Trova tutti i film di un utente con uno stato specifico (da vedere, già visto)
    List<UserMovie> findByUserIdAndShowStatus(UUID userId, ShowStatus showStatus);

    Optional<UserMovie> findByMovieId(long movieId);

    // Trova un film specifico nella lista di un utente
    Optional<UserMovie> findByUserIdAndMovieId(UUID userId, Long movieId);
}
