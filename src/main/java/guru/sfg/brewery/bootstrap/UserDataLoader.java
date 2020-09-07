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
import java.util.Set;

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
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        Role adminRole = Role.builder().name("ADMIN").authorities(Set.of(createBeer, updateBeer, readBeer, deleteBeer)).build();
        Role customerRole = Role.builder().name("CUSTOMER").authority(readBeer).build();
        Role userRole = Role.builder().name("USER").authority(readBeer).build();

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        userRepository.save(
                User.builder()
                        .username("spring")
                        .password(bCrypt.encode("guru"))
                        .role(adminRole)
                        .build());

        userRepository.save(
                User.builder()
                        .username("user")
                        .password(bCrypt.encode("password"))
                        .role(userRole)
                        .build());

        userRepository.save(
                User.builder()
                        .username("scott")
                        .password(bCrypt.encode("tiger"))
                        .role(customerRole)
                        .build());

        log.debug(userRepository.count() + " users created");

    }
}
