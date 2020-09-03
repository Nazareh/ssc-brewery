/*
 * Created by Nazareh on 3/9/20, 8:25 pm
 */

package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
}
