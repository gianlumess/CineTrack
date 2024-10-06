package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.Comment;
import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.payloads.CommentResponseDTO;
import gianlucamessina.CineTrack.payloads.NewCommentDTO;
import gianlucamessina.CineTrack.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;

    //FIND ALL COMMENTS OF A USER
    public List<Comment> findUserComments(UUID userId) {
        this.userService.findById(userId);

        return this.commentRepository.findByUserId(userId);
    }

    //FIND COMMENT BY USER ID AND SHOW ID
    public Comment findByUserIdAndShowId(UUID userId, long showId) {
        this.userService.findById(userId);

        return this.commentRepository.findByUserIdAndShowId(userId, showId).orElseThrow(() -> new NotFoundException(
                "Non esiste un commento dello show con ID: " + showId + " per l'utente con ID: " + userId));
    }

    //SAVE DI UN COMMMENT
    public CommentResponseDTO save(UUID userId, long showId, NewCommentDTO body) {
        User foundUser = this.userService.findById(userId);

        Optional<Comment> existingComment = this.commentRepository.findByUserIdAndShowId(userId, showId);

        if (existingComment.isPresent()) {
            Comment commentToUpdate = existingComment.get();

            commentToUpdate.setContent(body.content());
            commentToUpdate.setDateComment(LocalDate.now());
            this.commentRepository.save(commentToUpdate);

            return new CommentResponseDTO(commentToUpdate.getId(), commentToUpdate.getContent(), commentToUpdate.getShowId(),
                    commentToUpdate.getUser().getId(), commentToUpdate.getDateComment());
        } else {
            Comment newComment = new Comment(body.content(), showId, foundUser);
            this.commentRepository.save(newComment);

            return new CommentResponseDTO(newComment.getId(), newComment.getContent(), newComment.getShowId(), newComment.getUser().getId(),
                    newComment.getDateComment());
        }
    }

    //DELETE
    public void findByIdAndDelete(UUID userId, long showId) {
        Comment comment = this.findByUserIdAndShowId(userId, showId);
        this.commentRepository.delete(comment);
    }
}
