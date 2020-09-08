package guru.sfg.brewery.web.controllers.api;

/*
 *   Created by Nazareh on 8/9/20
 */

import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.security.permissions.BeerOrderCreatePermission;
import guru.sfg.brewery.security.permissions.BeerOrderPickUpPermission;
import guru.sfg.brewery.security.permissions.BeerOrderReadPermissionV2;
import guru.sfg.brewery.services.BeerOrderService;
import guru.sfg.brewery.web.model.BeerOrderDto;
import guru.sfg.brewery.web.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/customers/orders")
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
    @GetMapping("/{orderId}")
    public BeerOrderDto getOrder(@AuthenticationPrincipal User user,
                                 @PathVariable("orderId") UUID orderId) {

        return null;
        //  return beerOrderService.getOrderById(orderId);
    }
}
