package gianlucamessina.CineTrack.services;

import gianlucamessina.CineTrack.entities.User;
import gianlucamessina.CineTrack.exceptions.NotFoundException;
import gianlucamessina.CineTrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(UUID userId){
        return this.userRepository.findById(userId).orElseThrow(()->new NotFoundException(userId));
    }
}
