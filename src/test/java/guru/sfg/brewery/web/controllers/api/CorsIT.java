package guru.sfg.brewery.web.controllers.api;

/*
 *   Created by Nazareh on 15/9/20
 */

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CorsIT extends BaseIT {

    @WithUserDetails("spring")
    @Test
    void findBeersAUTH() throws Exception{
        mockMvc.perform(get("/api/v1/beer/")
        .header(HttpHeaders.ORIGIN,"https://springframework.guru"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*"));
    }

    @Test
    void findBeersPREFLIGHT() throws Exception{
        mockMvc.perform(options("/api/v1/beer/")
                .header(HttpHeaders.ORIGIN,"https://springframework.guru")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*"));
    }

    @Test
    void postBeersPREFLIGHT() throws Exception{
        mockMvc.perform(options("/api/v1/beer/")
                .header(HttpHeaders.ORIGIN,"https://springframework.guru")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,"POST"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*"));
    }

    @Test
    void putBeersPREFLIGHT() throws Exception{
        mockMvc.perform(options("/api/v1/beer/1234")
                .header(HttpHeaders.ORIGIN,"https://springframework.guru")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,"PUT"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*"));
    }

    @Test
    void deleteBeersPREFLIGHT() throws Exception{
        mockMvc.perform(options("/api/v1/beer/1234")
                .header(HttpHeaders.ORIGIN,"https://springframework.guru")
                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,"DELETE"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*"));
    }
}
