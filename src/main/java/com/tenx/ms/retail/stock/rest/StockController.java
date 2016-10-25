package com.tenx.ms.retail.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import com.tenx.ms.retail.stock.service.StockService;
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

@Api(value = "Stock", description = "Stock API")
@RestController("stockControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stock")

public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Add data to stock")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful stock update or addition"),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 404, message = "The store or the product were not found"),
        @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{storeId:\\d+}/{productId:\\d+}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createProduct(@PathVariable("storeId") Long storeId,
                              @PathVariable("productId") Long productId,
                              @Validated @RequestBody Stock stock) {

        stockService.addUpdateProductQuantity(storeId, productId, stock);

    }
}
