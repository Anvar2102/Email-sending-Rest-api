package uz.pdp.appjwtemailauth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjwtemailauth.Entity.Enum.RoleEnum;
import uz.pdp.appjwtemailauth.Entity.Role;

public interface RolRepository extends JpaRepository<Role, Integer> {
    Role findByRolename(RoleEnum rolename);
}
