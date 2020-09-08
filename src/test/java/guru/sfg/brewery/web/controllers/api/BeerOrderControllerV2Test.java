package guru.sfg.brewery.web.controllers.api;

/*
 *   Created by Nazareh on 8/9/20
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.brewery.bootstrap.DefaultBreweryLoader;
import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.domain.BeerOrder;
import guru.sfg.brewery.domain.Customer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderLineDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerOrderControllerV2Test extends BaseIT {

    public static final String API_ROOT = "/api/v2/customers/";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    Customer stPeteCustomer;
    Customer dunedinCustomer;
    Customer keyWestCustomer;
    List<Beer> loadedBeers;

    @BeforeEach
    void setUp(){
        stPeteCustomer = customerRepository.findByCustomerName(DefaultBreweryLoader.ST_PETE_DISTRIBUTING).orElseThrow();
        dunedinCustomer = customerRepository.findByCustomerName(DefaultBreweryLoader.DUNEDIN_DISTRIBUTING).orElseThrow();
        keyWestCustomer = customerRepository.findByCustomerName(DefaultBreweryLoader.KEY_WEST_DISTRIBUTORS).orElseThrow();
        loadedBeers = beerRepository.findAll();
    }

    @WithUserDetails("spring")
    @Test
    void listOrdersAdminAuth() throws Exception {
        mockMvc.perform(get(API_ROOT + "/orders"))
                .andExpect(status().isOk());
    }

    @WithUserDetails(DefaultBreweryLoader.STPETE_USER)
    @Test
    void listOrdersCustomerAuth() throws Exception {
        mockMvc.perform(get(API_ROOT + "/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void listOrdersNoAuth() throws Exception {
        mockMvc.perform(get(API_ROOT + "/orders"))
                .andExpect(status().isUnauthorized());
    }

}
