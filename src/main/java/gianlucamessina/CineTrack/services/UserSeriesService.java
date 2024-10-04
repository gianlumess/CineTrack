package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.entities.UserSeries;
import gianlucamessina.CineTrack.enums.ShowStatus;
import gianlucamessina.CineTrack.exceptions.BadRequestException;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.payloads.EditShowStatusDTO;
import gianlucamessina.CineTrack.payloads.NewUserSeriesDTO;
import gianlucamessina.CineTrack.payloads.UserSeriesResponseDTO;
import gianlucamessina.CineTrack.repositories.UserSeriesRepository;
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
public class UserSeriesService {
    @Autowired
    private UserSeriesRepository userSeriesRepository;
    @Autowired
    private UserService userService;

    //FIND ALL CON PAGINAZIONE
    public Page<UserSeries> findAll(int page, int size, String sortBy) {
        if (page > 150) page = 150;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userSeriesRepository.findAll(pageable);
    }

    //FIND ALL DI TUTTE LE SERIE DI UN UTENTE
    public List<UserSeriesResponseDTO> findAllMySeries(UUID userId) {
        User found = this.userService.findById(userId);

        List<UserSeries> userSeriesList = this.userSeriesRepository.findByUserId(userId);
        return userSeriesList.stream().map(userSeries -> new UserSeriesResponseDTO(
                userSeries.getId(),
                userSeries.getShowStatus(),
                userSeries.getUser().getId(),
                userSeries.getSeriesId(),
                userSeries.getDateAddedToList()
        )).collect(Collectors.toList());
    }

    //FIND SERIES BY ID
    public UserSeries findSeriesByIdInMyList(UUID userId, long seriesId) {
        return this.userSeriesRepository.findByUserIdAndSeriesId(userId, seriesId).orElseThrow(() -> new NotFoundException("La serie con ID: " + seriesId + " non è presente nella tua lista!"));
    }

    //SAVE DI UNA SERIE ALLA LISTA DI UN UTENTE
    public UserSeriesResponseDTO save(UUID userId, NewUserSeriesDTO body) {
        User found = this.userService.findById(userId);
        //mi assicuro che la serie che l'utente vuole salvare nella sua lista non sia già presente
        this.userSeriesRepository.findByUserIdAndSeriesId(userId, body.seriesId()).ifPresent(userMovie -> {
            throw new BadRequestException("La serie è già presente nella tua lista!");
        });

        UserSeries newUserSeries = new UserSeries(ShowStatus.valueOf(body.showStatus()), found, body.seriesId());
        this.userSeriesRepository.save(newUserSeries);
        return new UserSeriesResponseDTO(newUserSeries.getId(), newUserSeries.getShowStatus(), userId, newUserSeries.getSeriesId(),
                newUserSeries.getDateAddedToList());

    }

    //DELETE DI UNA SEIE NELLA LISTA DELL'UTENTE
    public void deleteSeriesFromMyList(UUID userId, long seriesId) {
        User found = this.userService.findById(userId);

        UserSeries foundSeries = this.findSeriesByIdInMyList(userId, seriesId);

        this.userSeriesRepository.delete(foundSeries);
    }

    //EDIT SHOWSTATUS
    public UserSeriesResponseDTO editShowStatus(UUID userId, long seriesId, EditShowStatusDTO body) {
        this.userService.findById(userId);
        UserSeries foundSeries = this.findSeriesByIdInMyList(userId, seriesId);

        foundSeries.setShowStatus(ShowStatus.valueOf(body.showStatus()));
        return new UserSeriesResponseDTO(foundSeries.getId(), foundSeries.getShowStatus(), foundSeries.getUser().getId(),
                foundSeries.getSeriesId(), foundSeries.getDateAddedToList());
    }

    //FIND SERIES BY USER ID AND SHOW STATUS
    public List<UserSeriesResponseDTO> findByUserIdAndShowStatus(UUID userId, ShowStatus showStatus) {
        User found = this.userService.findById(userId);

        List<UserSeries> userSeriesList = this.userSeriesRepository.findByUserIdAndShowStatus(userId, showStatus);

        return userSeriesList.stream().map(userSeries -> new UserSeriesResponseDTO(
                userSeries.getId(),
                userSeries.getShowStatus(),
                userSeries.getUser().getId(),
                userSeries.getSeriesId(),
                userSeries.getDateAddedToList()
        )).collect(Collectors.toList());
    }
}
