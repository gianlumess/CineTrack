package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.entities.UserMovie;
import gianlucamessina.CineTrack.enums.ShowStatus;
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

    //SAVE DI UN FILM ALLA LISTA DI UN UTENTE
    public UserMovieResponseDTO save(UUID userId, NewUserMovieDTO body) {
        User found = this.userService.findById(userId);

        UserMovie newUserMovie = new UserMovie(ShowStatus.valueOf(body.showStatus()), found, body.movieId());
        this.userMovieRepository.save(newUserMovie);
        return new UserMovieResponseDTO(newUserMovie.getId(), newUserMovie.getShowStatus(), userId, newUserMovie.getMovieId(),
                newUserMovie.getDateAddedToList());

    }
}
