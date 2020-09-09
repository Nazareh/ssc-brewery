package guru.sfg.brewery.security.listeners;

/*
 *   Created by Nazareh on 9/9/20
 */

import guru.sfg.brewery.domain.security.LoginSucces;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent event){

        LoginSucces.LoginSuccesBuilder loginSuccesBuilder = LoginSucces.builder();

        log.debug("User Logged in Okay");
        if(event.getSource() instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof User){
                User user = (User) token.getPrincipal();
                loginSuccesBuilder.user(user);
                log.debug("Username logged in:" + user.getUsername());
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                loginSuccesBuilder.sourceIp(details.getRemoteAddress());
                log.debug("Source IP:" + details.getRemoteAddress());
            }

            loginSuccessRepository.save(loginSuccesBuilder.build());
        }
    }
}
