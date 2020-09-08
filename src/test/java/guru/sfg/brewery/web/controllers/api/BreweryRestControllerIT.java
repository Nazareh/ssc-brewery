/*
 * Created by Nazareh on 7/9/20, 10:31 am
 */

package guru.sfg.brewery.web.controllers.api;


import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweryRestControllerIT extends BaseIT {
    @Test
    void getBreweriesHttpBasicUserRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBreweriesHttpBasicCustomerRole() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("scott","tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getBreweriesHttpBasicAdmin() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("spring","guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getBreweriesBreweryNoAuth() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }
}
