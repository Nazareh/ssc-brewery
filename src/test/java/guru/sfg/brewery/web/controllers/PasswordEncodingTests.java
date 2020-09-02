
/*
 * Created by Nazareh on 2/9/20, 9:59 am
 */

package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

public class PasswordEncodingTests {

    @Test
    void testBcrypt(){
        PasswordEncoder bcrypt = new BCryptPasswordEncoder();
        System.out.println(bcrypt.encode("supersecret"));
    }

    @Test
    void testSha256(){
        PasswordEncoder sha256 = new StandardPasswordEncoder();

        System.out.println(sha256.encode("password"));
    }

    @Test
    void testLdap(){
        PasswordEncoder ldap = new LdapShaPasswordEncoder();
        String encodedPassword = ldap.encode("tiger");
        System.out.println(encodedPassword);
    }

    @Test
    void testNoOp(){
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();
        System.out.println(noOp.encode("PASSWORD"));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex("PASSWORD".getBytes()));
    }
}
