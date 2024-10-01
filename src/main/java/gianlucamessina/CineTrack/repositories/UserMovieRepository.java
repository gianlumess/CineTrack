package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, UUID> {
}
