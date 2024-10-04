package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.entities.UserMovie;
import gianlucamessina.CineTrack.enums.ShowStatus;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.EditShowStatusDTO;
import gianlucamessina.CineTrack.payloads.NewUserMovieDTO;
import gianlucamessina.CineTrack.payloads.UserMovieResponseDTO;
import gianlucamessina.CineTrack.services.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user_movies")
public class UserMovieController {
    @Autowired
    private UserMovieService userMovieService;

    //FIND ALL MOVIES LISTS
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserMovie> findAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        return this.userMovieService.findAll(page, size, sortBy);
    }

    //SAVE DI UN FILM NELLA LISTA DELL'UTENTE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserMovieResponseDTO save(@AuthenticationPrincipal User user, @RequestBody @Validated NewUserMovieDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.userMovieService.save(user.getId(), body);
    }

    //FIND ALL FILM DI UN UTENTE
    @GetMapping("/me")
    public List<UserMovieResponseDTO> findAllMyMovies(@AuthenticationPrincipal User user) {
        return this.userMovieService.findAllMyMovies(user.getId());
    }

    //GET MOVIES LIST BY SHOW STATUS
    @GetMapping("/me/status")
    public List<UserMovieResponseDTO> findByUserIdAndShowStatus(@AuthenticationPrincipal User user, @RequestParam("showStatus") ShowStatus showStatus) {
        return this.userMovieService.findByUserIdAndShowStatus(user.getId(), showStatus);
    }

    //DELETE DI UN FILM DALLA LISTA DI UN UTENTE
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovieFromMyList(@AuthenticationPrincipal User user, @RequestParam("movieId") long movieId) {
        this.userMovieService.deleteMovieFromMyList(user.getId(), movieId);
    }

    //EDIT SHOWSTATUS
    @PatchMapping("/me")
    public UserMovieResponseDTO editShowStatus(@AuthenticationPrincipal User user, @RequestParam("movieId") long movieId, @RequestBody @Validated EditShowStatusDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.userMovieService.editShowStatus(user.getId(), movieId, body);
    }


}
