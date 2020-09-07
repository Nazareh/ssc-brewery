package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder bCrypt;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() + authorityRepository.count() + roleRepository.count() == 0) {
            loadSecurityData();
        }
    }

    private void loadSecurityData() {

        User spring = userRepository.save(
                User.builder().username("spring").password(bCrypt.encode("guru")).build());

        User user = userRepository.save(
                User.builder().username("user").password(bCrypt.encode("password")).build());

        User scott = userRepository.save(
                User.builder().username("scott").password(bCrypt.encode("tiger")).build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        Authority createCustomer = authorityRepository.save(Authority.builder().permission("customer.create").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission("customer.read").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission("customer.update").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission("customer.delete").build());

        Authority createBrewery = authorityRepository.save(Authority.builder().permission("brewery.create").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission("brewery.read").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission("brewery.update").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission("brewery.delete").build());

        adminRole.setAuthorities(new HashSet<>(authorityRepository.findAll()));
        customerRole.setAuthorities(new HashSet<>(Set.of(readBeer, readCustomer, readBrewery)));
        userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));

        spring.setRoles(new HashSet<>(Set.of(adminRole)));
        scott.setRoles(new HashSet<>(Set.of(customerRole)));
        user.setRoles(new HashSet<>(Set.of(userRole)));

        userRepository.saveAll(Arrays.asList(spring, scott, user));
        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        log.debug("Users Loaded: " + userRepository.count());
        log.debug("Roles Loaded: " + roleRepository.count());
        log.debug("Authorities Loaded: " + authorityRepository.count());
    }
}
