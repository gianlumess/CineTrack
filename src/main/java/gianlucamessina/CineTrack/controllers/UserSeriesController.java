package gianlucamessina.CineTrack.controllers;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.entities.UserSeries;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.payloads.EditShowStatusDTO;
import gianlucamessina.CineTrack.payloads.NewUserSeriesDTO;
import gianlucamessina.CineTrack.payloads.UserSeriesResponseDTO;
import gianlucamessina.CineTrack.services.UserSeriesService;
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
@RequestMapping("/user_series")
public class UserSeriesController {
    @Autowired
    private UserSeriesService userSeriesService;

    //FIND ALL SERIES LISTS
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserSeries> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "15") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.userSeriesService.findAll(page, size, sortBy);
    }

    //SAVE DI UNA SERIE NELLA LISTA DELL'UTENTE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserSeriesResponseDTO save(@AuthenticationPrincipal User user, @RequestBody @Validated NewUserSeriesDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.userSeriesService.save(user.getId(), body);
    }

    //FIND ALL SERIES DI UN UTENTE
    @GetMapping("/me")
    public List<UserSeriesResponseDTO> findAllMySeries(@AuthenticationPrincipal User user) {
        return this.userSeriesService.findAllMySeries(user.getId());
    }

    //DELETE DI UNA SERIE DALLA LISTA DI UN UTENTE
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeriesFromMyList(@AuthenticationPrincipal User user, @RequestParam("seriesId") long seriesId) {
        this.userSeriesService.deleteSeriesFromMyList(user.getId(), seriesId);
    }

    @PatchMapping("/me")
    public UserSeriesResponseDTO editShowStatus(@AuthenticationPrincipal User user, @RequestParam("seriesId") long seriesId, @RequestBody @Validated EditShowStatusDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));

            throw new BadRequestException("ci sono stati errori nel payload: " + messages);
        }
        return this.userSeriesService.editShowStatus(user.getId(), seriesId, body);
    }
}
