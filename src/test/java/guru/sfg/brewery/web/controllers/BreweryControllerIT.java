/*
 * Created by Nazareh on 7/9/20, 10:39 am
 */

package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BreweryControllerIT  extends BaseIT{

    @Test
    void getBreweriesWithCustomer() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @Test
    void getBreweriesWithUser() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBreweriesNoAuth() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(anonymous()))
                .andExpect(status().isUnauthorized());
    }
}
