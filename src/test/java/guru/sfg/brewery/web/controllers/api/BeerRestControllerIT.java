/*
 * Created by Nazareh on 1/9/20, 10:02 am
 */

package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests {
        public Beer beerToDelete() {

            Random random = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("Delete Me Beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(random.nextInt(999999999)))
                    .build());
        }

        @Test
        void deleteBeerBadCreds() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .header("Api-Key", "admin")
                    .header("Api-Secret", "wrong password"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteHttpBasicUserRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteHttpBasicCustomerRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteHttpBasicAdminRole() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId())
                    .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }


        @Test
        void deleteBeerNoAuth() throws Exception {
            mockMvc.perform(delete("/api/v1/beer/" + beerToDelete().getId()))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeerByIdNoAuth() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/" + beer.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeerByIdAdmin() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/" + beer.getId())
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByIdCustomer() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/" + beer.getId())
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/" + beer.getId())
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpcNoAuth() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeerByUpcAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpcUser() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpcCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerFormAdmin() throws Exception {
        mockMvc.perform(get("/beers").param("beerName", "")
                .with(httpBasic("spring", "guru")))
                .andExpect(status().isOk());
    }
}
