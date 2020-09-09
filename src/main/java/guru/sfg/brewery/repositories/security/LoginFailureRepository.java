
package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
/*
 * Created by Nazareh on 3/9/20, 8:25 pm
 */

public interface LoginFailureRepository extends JpaRepository<LoginFailure,Integer> {

    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);
}
