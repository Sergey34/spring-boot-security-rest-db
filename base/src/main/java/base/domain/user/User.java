package base.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "username", unique = true)
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
}
