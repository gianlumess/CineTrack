package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.entities.UserMovie;
import gianlucamessina.CineTrack.enums.ShowStatus;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.payloads.EditShowStatusDTO;
import gianlucamessina.CineTrack.payloads.NewUserMovieDTO;
import gianlucamessina.CineTrack.payloads.UserMovieResponseDTO;
import gianlucamessina.CineTrack.repositories.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserMovieService {
    @Autowired
    UserService userService;
    @Autowired
    private UserMovieRepository userMovieRepository;

    //FIND ALL CON PAGINAZIONE
    public Page<UserMovie> findAll(int page, int size, String sortBy) {
        if (page > 150) page = 150;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userMovieRepository.findAll(pageable);
    }

    //FIND ALL DI TUTTI I FILM DI UN UTENTE
    public List<UserMovieResponseDTO> findAllMyMovies(UUID userId) {
        User found = this.userService.findById(userId);

        List<UserMovie> userMoviesList = this.userMovieRepository.findByUserId(userId);
        return userMoviesList.stream().map(userMovie -> new UserMovieResponseDTO(
                userMovie.getId(),
                userMovie.getShowStatus(),
                userMovie.getUser().getId(),
                userMovie.getMovieId(),
                userMovie.getDateAddedToList()
        )).collect(Collectors.toList());
    }

    //FIND MOVIE BY ID
    public UserMovie findMovieByIdInMyList(UUID userId, long movieId) {
        return this.userMovieRepository.findByUserIdAndMovieId(userId, movieId).orElseThrow(() -> new NotFoundException("Il film con ID: " + movieId + " non è presente nella tua lista!"));
    }

    //SAVE DI UN FILM ALLA LISTA DI UN UTENTE
    public UserMovieResponseDTO save(UUID userId, NewUserMovieDTO body) {
        User found = this.userService.findById(userId);
        //mi assicuro che il film che l'utente vuole salvare nella sua lista non sia già presente
        this.userMovieRepository.findByUserIdAndMovieId(userId, body.movieId()).ifPresent(userMovie -> {
            throw new BadRequestException("Il film è già presente nella tua lista!");
        });

        UserMovie newUserMovie = new UserMovie(ShowStatus.valueOf(body.showStatus()), found, body.movieId());
        this.userMovieRepository.save(newUserMovie);
        return new UserMovieResponseDTO(newUserMovie.getId(), newUserMovie.getShowStatus(), userId, newUserMovie.getMovieId(),
                newUserMovie.getDateAddedToList());

    }

    //DELETE DI UN FILM NELLA LISTA DELL'UTENTE
    public void deleteMovieFromMyList(UUID userId, long movieId) {
        User found = this.userService.findById(userId);

        UserMovie foundMovie = this.findMovieByIdInMyList(userId, movieId);

        this.userMovieRepository.delete(foundMovie);
    }

    //EDIT SHOWSTATUS
    public UserMovieResponseDTO editShowStatus(UUID userId, long movieId, EditShowStatusDTO body) {
        this.userService.findById(userId);
        UserMovie foundMovie = this.findMovieByIdInMyList(userId, movieId);

        foundMovie.setShowStatus(ShowStatus.valueOf(body.showStatus()));
        return new UserMovieResponseDTO(foundMovie.getId(), foundMovie.getShowStatus(), foundMovie.getUser().getId(),
                foundMovie.getMovieId(), foundMovie.getDateAddedToList());
    }

    //FIND MOVIES BY USER ID AND SHOW STATUS
    public List<UserMovieResponseDTO> findByUserIdAndShowStatus(UUID userId, ShowStatus showStatus) {
        User found = this.userService.findById(userId);

        List<UserMovie> userMovieList = this.userMovieRepository.findByUserIdAndShowStatus(userId, showStatus);

        return userMovieList.stream().map(userMovie -> new UserMovieResponseDTO(
                userMovie.getId(),
                userMovie.getShowStatus(),
                userMovie.getUser().getId(),
                userMovie.getMovieId(),
                userMovie.getDateAddedToList()
        )).collect(Collectors.toList());
    }
}
