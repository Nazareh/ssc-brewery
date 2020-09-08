package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseIT {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUpBaseIt() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(
                Arguments.arguments("spring", "guru"),
                Arguments.arguments("user", "password"),
                Arguments.arguments("scott", "tiger")
        );
    }

    public static Stream<Arguments> getStreamAdminUsers() {
        return Stream.of(
                Arguments.arguments("spring", "guru")
        );
    }

    public static Stream<Arguments> getStreamNotAdminUsers() {
        return Stream.of(
                Arguments.arguments("user", "password"),
                Arguments.arguments("scott", "tiger")
        );
    }
}
