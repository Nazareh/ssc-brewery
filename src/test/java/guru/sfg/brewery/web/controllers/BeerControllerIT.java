package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Nazareh on 07-sept-20.
 */
@SpringBootTest
public class BeerControllerIT extends BaseIT {

    BeerRepository beerRepository;

    @DisplayName("Init New Form")
    @Nested
    class InitNewForm {

        @ParameterizedTest(name = "Test {index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAllUsers")
        void initCreationFormAuth(String user, String password) throws Exception {
            mockMvc.perform(get("/beers/new").with(httpBasic(user, password)))
                    .andExpect(status().isOk())
                    .andExpect(view().name("beers/createBeer"))
                    .andExpect(model().attributeExists("beer"));
        }

        @Test
        void initCreationFormNoAuth() throws Exception {
            mockMvc.perform(get("/beers/new"))
                    .andExpect(status().isUnauthorized());
        }
    }
    @DisplayName("List Beers")
    @Nested
    class ListBeers{
        @Test
        void findBeers() throws Exception {
            mockMvc.perform(get("/beers/find"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeersWithAnonymous() throws Exception {
            mockMvc.perform(get("/beers/find").with(anonymous()))
                    .andExpect(status().isUnauthorized());
        }
        @ParameterizedTest
        @MethodSource("guru.sfg.brewery.web.controllers.BeerControllerIT#getStreamAllUsers")
        void findBeers(String username, String password) throws Exception {
            mockMvc.perform(get("/beers/find")
                    .with(httpBasic(username, password)))
                    .andExpect(status().isOk())
                    .andExpect(view().name("beers/findBeers"))
                    .andExpect(model().attributeExists("beer"));
        }
    }
}
