package uz.pdp.appjwtemailauth.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjwtemailauth.Entity.User;


import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEmailcode(@Email String email, String emailcode);

    Optional<User> findByEmail(@Email String email);

}
