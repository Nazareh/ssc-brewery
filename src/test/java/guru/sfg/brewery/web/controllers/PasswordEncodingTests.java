/*
 * Created by Nazareh on 2/9/20, 10:14 am
 */

/*
 * Created by Nazareh on 2/9/20, 9:59 am
 */

package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    static final String PASSWORD = "password";


    @Test
    void testNoOp(){
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        System.out.println(noOp.encode(PASSWORD));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));
    }
}
