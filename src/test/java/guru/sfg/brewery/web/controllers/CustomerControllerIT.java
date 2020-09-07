/*
 * Created by Nazareh on 7/9/20, 2:42 pm
 */

package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @DisplayName("Add Customers")
    @Nested
    class AddCustomers{

        @Rollback
        @Test
        void proccessCreationForm() throws Exception{
            mockMvc.perform(post("/customers/new")
                    .param("customerName","Foo Customer")
                    .with(httpBasic("spring","guru")))
                    .andExpect(status().is3xxRedirection());
        }

        @Rollback
        @ParameterizedTest
        @MethodSource("guru.sfg.brewery.web.controllers.CustomerControllerIT#getStreamNotAdminUsers")
        void proccessCreationFormNotAuth(String username, String password) throws Exception{
            mockMvc.perform(post("/customers/new")
                    .param("customerName","Foo Customer2")
                    .with(httpBasic(username,password)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void proccessCreationFormNoAuth() throws Exception{
            mockMvc.perform(post("/customers/new")
                    .param("customerName","Foo Customer2"))
                    .andExpect(status().isUnauthorized());
        }

    }
}
