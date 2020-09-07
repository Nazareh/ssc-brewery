/*
 * Created by Nazareh on 7/9/20, 2:42 pm
 */

package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerIT extends BaseIT{

    @ParameterizedTest
    @MethodSource("getStreamAdminUsers")
    void testListCustomerAuth(String username, String password) throws Exception {

        mockMvc.perform(get("/customers")
                .with(httpBasic(username,password)))
                .andExpect(status().isOk());
    }

    @Test
    void testListCustomerNotAuth() throws Exception {
        mockMvc.perform(get("/customers")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testListCustomerNotLoggedIn() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }
}
