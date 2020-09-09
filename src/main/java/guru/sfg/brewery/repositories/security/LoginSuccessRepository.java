
package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;
/*
 * Created by Nazareh on 3/9/20, 8:25 pm
 */

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess,Integer> {
}
