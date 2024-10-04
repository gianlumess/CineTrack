package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.UserSeries;
import gianlucamessina.CineTrack.enums.ShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserSeriesRepository extends JpaRepository<UserSeries, UUID> {
    //TROVA TUTTE LE SERIE DI UN UTENTE
    List<UserSeries> findByUserId(UUID userId);

    // TROVA UNA SERIE SPECIFICA NELLA LISTA DI UN UTENTE
    Optional<UserSeries> findByUserIdAndSeriesId(UUID userId, Long seriesId);

    // Trova tutte le serie di un utente con uno stato specifico (da vedere, già visto)
    List<UserSeries> findByUserIdAndShowStatus(UUID userId, ShowStatus showStatus);
}
