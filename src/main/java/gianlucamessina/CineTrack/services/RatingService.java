package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.Rating;
import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.payloads.NewRatingDTO;
import gianlucamessina.CineTrack.payloads.RatingResponseDTO;
import gianlucamessina.CineTrack.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserService userService;

    //Ritorna la lista di tutte le valutazioni di un utente
    public List<Rating> findUserRatings(UUID userId) {
        this.userService.findById(userId);

        return this.ratingRepository.findByUserId(userId);
    }

    //FIND RATING BY ID
    public Rating findByUserIdAndShowId(UUID userId, long showId) {
        this.userService.findById(userId);

        return this.ratingRepository.findByUserIdAndShowId(userId, showId).orElseThrow(() -> new NotFoundException(
                "Non esiste una valutazione dello show con ID: " + showId + " per l'utente con ID: " + userId));

    }

    //SAVE DI UN RATING
    public RatingResponseDTO save(NewRatingDTO body) {
        User foundUser = this.userService.findById(UUID.fromString(body.userId()));

        // controllo se esiste già una valutazione per lo stesso showId e userId
        Rating existingRating = this.findByUserIdAndShowId(UUID.fromString(body.userId()), body.showId());

        if (existingRating != null) {
            // Aggiorna la valutazione e la data
            existingRating.setRating(body.rating());
            existingRating.setDateRating(LocalDate.now());
            this.ratingRepository.save(existingRating);
            return new RatingResponseDTO(existingRating.getId(),
                    existingRating.getRating(), existingRating.getShowId(),
                    existingRating.getUser().getId(), existingRating.getDateRating());
        } else {
            // Crea una nuova valutazione
            Rating newRating = new Rating(body.rating(), body.showId(), foundUser);
            return new RatingResponseDTO(newRating.getId(),
                    newRating.getRating(), newRating.getShowId(),
                    newRating.getUser().getId(), newRating.getDateRating());
        }
    }

    //DELETE
    public void findByIdAndDelete(UUID userId, long showId) {
        Rating rating = this.findByUserIdAndShowId(userId, showId);
        this.ratingRepository.delete(rating);
    }
}
