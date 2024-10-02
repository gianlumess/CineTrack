package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.UserMovie;
import gianlucamessina.CineTrack.repositories.UserMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserMovieService {
    @Autowired
    private UserMovieRepository userMovieRepository;

    //FIND ALL CON PAGINAZIONE
    public Page<UserMovie> findAll(int page,int size,String sortBy){
        if(page>150)page=150;

        Pageable pageable= PageRequest.of(page,size, Sort.by(sortBy));
        return this.userMovieRepository.findAll(pageable);
    }

    //SAVE DI UN FILM ALLA LISTA DI UN UTENTE
    public UserMovie save(UserMovie movie){
        return this.userMovieRepository.save(movie);
    }
}
