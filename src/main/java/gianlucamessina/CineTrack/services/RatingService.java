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
import java.util.Optional;
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

    //FIND RATING BY USER ID AND SHOW ID
    public Rating findByUserIdAndShowId(UUID userId, long showId) {
        this.userService.findById(userId);

        return this.ratingRepository.findByUserIdAndShowId(userId, showId).orElseThrow(() -> new NotFoundException(
                "Non esiste una valutazione dello show con ID: " + showId + " per l'utente con ID: " + userId));

    }

    //SAVE DI UN RATING
    public RatingResponseDTO save(UUID userId, long showId, NewRatingDTO body) {
        User foundUser = this.userService.findById(userId);

        // controllo se esiste già una valutazione per lo stesso showId e userId
        Optional<Rating> existingRating = this.ratingRepository.findByUserIdAndShowId(userId, showId);

        if (existingRating.isPresent()) {
            // Aggiorna la valutazione e la data
            Rating ratingToUpdate = existingRating.get();

            ratingToUpdate.setRating(body.rating());
            ratingToUpdate.setDateRating(LocalDate.now());
            this.ratingRepository.save(ratingToUpdate);

            return new RatingResponseDTO(ratingToUpdate.getId(),
                    ratingToUpdate.getRating(), ratingToUpdate.getShowId(),
                    ratingToUpdate.getUser().getId(), ratingToUpdate.getDateRating());
        } else {
            // Crea una nuova valutazione
            Rating newRating = new Rating(body.rating(), showId, foundUser);
            this.ratingRepository.save(newRating);
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
