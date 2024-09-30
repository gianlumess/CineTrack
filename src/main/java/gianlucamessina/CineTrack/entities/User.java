package gianlucamessina.CineTrack.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gianlucamessina.CineTrack.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate creationDate;
    @OneToMany(mappedBy = "user")
    private List<UserMovie> userMovies;
    @OneToMany(mappedBy = "user")
    private List<UserSeries> userSeries;

    public User(String name, String surname, String username, String email, String password, String avatar, Role role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.creationDate = LocalDate.now();
    }
}
