package gianlucamessina.CineTrack.repositories;

import gianlucamessina.CineTrack.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    // Trova il commento di un utente per uno specifico film/serie
    Optional<Comment> findByUserIdAndShowId(UUID userId, long showId);

    // Trova tutti i commenti di un utente specifico
    List<Comment> findByUserId(UUID userId);
}
