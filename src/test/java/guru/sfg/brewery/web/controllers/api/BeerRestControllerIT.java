/*
 * Created by Nazareh on 1/9/20, 10:02 am
 */

package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923")
                .header("Api-Key", "admin")
                .header("Api-Secret", "wrong password"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteHttpBasicUserRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923")
                .with(httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteHttpBasicCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923")
                .with(httpBasic("scott","tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteHttpBasicAdminRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923")
                        .with(httpBasic("spring","guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        mockMvc.perform(get("/api/v1/beer/b4467073-f329-4998-af3b-83cfa79f5923"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }
}
