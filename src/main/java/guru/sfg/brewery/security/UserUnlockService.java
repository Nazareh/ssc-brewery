package guru.sfg.brewery.security;

/*
 *   Created by Nazareh on 11/9/20
 */

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUnlockService {
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 300000)
    public void unlockAccounts(){
        log.debug("Running Unlock Accounts");

        List<User> lockedUsers = userRepository.findAllByAccountNonLockedAndLastUpdateDateIsAfter(
                false, Timestamp.valueOf(LocalDateTime.now().minusSeconds(5))
        );

        if(lockedUsers.size() >0){
            lockedUsers.forEach( user -> {
                user.setAccountNonLocked(true);
                log.debug(user.getUsername() + " Account Unlocked");
            });
            userRepository.saveAll(lockedUsers);
        }

    }

}
