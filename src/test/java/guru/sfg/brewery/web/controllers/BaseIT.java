package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class BaseIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(
                Arguments.arguments("spring", "guru"),
                Arguments.arguments("user", "password"),
                Arguments.arguments("scott", "tiger")
        );
    }

    static Stream<Arguments> getStreamAdminUsers() {
        return Stream.of(
                Arguments.arguments("spring", "guru")
        );
    }

    static Stream<Arguments> getStreamNotAdminUsers() {
        return Stream.of(
                Arguments.arguments("user", "password"),
                Arguments.arguments("scott", "tiger")
        );
    }
}
