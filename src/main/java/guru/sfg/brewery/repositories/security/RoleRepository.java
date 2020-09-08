
package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
 * Created by Nazareh on 3/9/20, 8:25 pm
 */

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String customer);
}
