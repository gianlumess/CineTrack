package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.Comment;
import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.CommentResponseDTO;
import gianlucamessina.CineTrack.payloads.NewCommentDTO;
import gianlucamessina.CineTrack.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //GET ALL MY COMMENTS
    @GetMapping("/me")
    public List<Comment> findMyComments(@AuthenticationPrincipal User user) {
        return this.commentService.findUserComments(user.getId());
    }

    //GET COMMENT OF A SPECIFIC SHOW
    @GetMapping("/me/{showId}")
    public CommentResponseDTO getMyCommentByShowId(@AuthenticationPrincipal User user, @PathVariable long showId) {
        Comment found = this.commentService.findByUserIdAndShowId(user.getId(), showId);
        return new CommentResponseDTO(found.getId(), found.getContent(), found.getShowId(), found.getUser().getId(),
                found.getDateComment());
    }

    //SAVE
    @PostMapping("/me/{showId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDTO save(@AuthenticationPrincipal User user, @PathVariable long showId, @RequestBody @Validated NewCommentDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.commentService.save(user.getId(), showId, body);
    }

    //DELETE
    @DeleteMapping("/me/{showId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentOfSpecificShow(@AuthenticationPrincipal User user, @PathVariable long showId) {
        this.commentService.findByIdAndDelete(user.getId(), showId);
    }
}
