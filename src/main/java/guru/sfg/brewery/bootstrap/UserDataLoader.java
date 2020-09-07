package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder bCrypt;

    private Authority ADMIN_ROLE;
    private Authority CUSTOMER_ROLE;
    private Authority USER_ROLE;


    @Override
    public void run(String... args) throws Exception {

        if(authorityRepository.count() == 0){
            loadSecurityData();
        }

    }

    private void loadSecurityData() {
        loadAuthorities();
        loadUsers();
    }

    private void loadAuthorities(){

        if (authorityRepository.count() == 0 ) {
            ADMIN_ROLE = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            USER_ROLE = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
            CUSTOMER_ROLE = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());
            log.debug(authorityRepository.count() + " roles created");
        }
    }

    private void loadUsers(){
        final String SPRING = "spring";
        final String USER = "user";
        final String SCOTT = "scott";

        if (userRepository.findByUsername(SPRING).isEmpty()) {
            userRepository.save(
                    User.builder()
                            .username(SPRING)
                            .password(bCrypt.encode("guru"))
                            .authority(ADMIN_ROLE)
                            .build());
            log.debug(SPRING + " user created");
        }

        if (userRepository.findByUsername(USER).isEmpty()) {
            userRepository.save(
                    User.builder()
                            .username(USER)
                            .password(bCrypt.encode("password"))
                            .authority(USER_ROLE)
                            .build());
            log.debug(USER + " user created");
        }

        if (userRepository.findByUsername(SCOTT).isEmpty()) {
            userRepository.save(
                    User.builder()
                            .username(SCOTT)
                            .password(bCrypt.encode("tiger"))
                            .authority(CUSTOMER_ROLE)
                            .build());
            log.debug(SCOTT + " user created");
        }
    }
}
