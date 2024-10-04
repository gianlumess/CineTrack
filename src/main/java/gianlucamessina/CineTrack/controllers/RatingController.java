package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.Rating;
import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.NewRatingDTO;
import gianlucamessina.CineTrack.payloads.RatingResponseDTO;
import gianlucamessina.CineTrack.services.RatingService;
import gianlucamessina.CineTrack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;

    //GET ALL MY RATINGS
    @GetMapping("/me")
    public List<Rating> findMyRatings(@AuthenticationPrincipal User user) {
        return this.ratingService.findUserRatings(user.getId());
    }

    //GET RATING OF A SPECIFIC SHOW
    @GetMapping("/me/{showId}")
    public Rating getMyRatingByShowId(@AuthenticationPrincipal User user, @PathVariable long showId) {
        return this.ratingService.findByUserIdAndShowId(user.getId(), showId);
    }

    //SAVE
    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponseDTO save(@AuthenticationPrincipal User user, @RequestBody @Validated NewRatingDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.ratingService.save(user.getId(), body);
    }

    //DELETE
    @DeleteMapping("/me/{showId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRatingOfSpecificShow(@AuthenticationPrincipal User user, @PathVariable long showId) {
        this.ratingService.findByIdAndDelete(user.getId(), showId);
    }
}
