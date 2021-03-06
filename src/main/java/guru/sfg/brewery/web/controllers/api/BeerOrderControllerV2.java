package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.security.permissions.BeerOrderReadPermissionV2;
import guru.sfg.brewery.services.BeerOrderService;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/*
 *   Created by Nazareh on 8/9/20
 */
@Slf4j
@RequestMapping("/api/v2/orders")
@RestController
@RequiredArgsConstructor
public class BeerOrderControllerV2 {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderService beerOrderService;

    @BeerOrderReadPermissionV2
    @GetMapping
    public BeerOrderPagedList listOrder(@AuthenticationPrincipal User user,
                                        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
       return user.getCustomer() != null
               ? beerOrderService.listOrders(user.getCustomer().getId(),PageRequest.of(pageNumber, pageSize))
               : beerOrderService.listOrders(PageRequest.of(pageNumber, pageSize));
    }

    @BeerOrderReadPermissionV2
    @GetMapping("{orderId}")
    public BeerOrderDto getOrder(@PathVariable("orderId") UUID orderId){
        BeerOrderDto beerOrderDto =  beerOrderService.getOrderById(orderId);

        if (beerOrderDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found");
        }

        log.debug("Found Order: " + beerOrderDto);

        return beerOrderDto;
    }
}