package guru.sfg.brewery.security.listeners;

/*
 *   Created by Nazareh on 9/9/20
 */

import guru.sfg.brewery.domain.security.LoginFailure;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginFailureRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class AuthenticationBadCredentialsListener {

    private final UserRepository userRepository;
    private final LoginFailureRepository loginFailureRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event) {

        log.debug("Login failure");
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

            if (token.getPrincipal() instanceof String) {
                String username = (String) token.getPrincipal();
                builder.username(username);
                builder.user(userRepository.findByUsername(username).orElse(null));
                log.debug("Failed attempt to login with username: " + username);
            }

            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                builder.sourceIp(details.getRemoteAddress());
                log.debug("Source IP:" + details.getRemoteAddress());
            }
            LoginFailure loginFailure = loginFailureRepository.save(builder.build());
            log.debug("Failure event:" + loginFailure.getId());

            if (loginFailure.getUser() != null){
                lockUserAccount(loginFailure.getUser());
            }
        }
    }

    private void lockUserAccount(User user) {
        List<LoginFailure> loginFailureList = loginFailureRepository
                .findAllByUserAndCreatedDateIsAfter(user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

        if(loginFailureList.size() > 3){
            user.setAccountNonLocked(false);
            userRepository.save(user);
            log.debug(user.getUsername() + " username has been locked due to too many failed attempts");
        }
    }
}
