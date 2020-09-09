package guru.sfg.brewery.security.listeners;

/*
 *   Created by Nazareh on 9/9/20
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessListener {

    @EventListener
    public void listen(AuthenticationSuccessEvent event){
        log.debug("User Logged in Okay");
    }
}
