package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Order", description = "Order API")
@RestController("orderControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Create a new Order")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful creation of an order"),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Order createOrder(@PathVariable("storeId") Long storeId,
                                               @Validated @RequestBody Order order) {
        return orderService.createOrder(storeId, order);
    }
}
