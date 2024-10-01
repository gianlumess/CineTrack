package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.UserSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSeriesRepository extends JpaRepository<UserSeries, UUID> {
}
