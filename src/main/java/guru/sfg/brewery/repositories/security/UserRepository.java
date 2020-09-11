/*
 * Created by Nazareh on 3/9/20, 8:24 pm
 */

package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAllByAccountNonLockedAndLastUpdateDateIsAfter(Boolean nonLockedAccount, Timestamp timestamp);

}
