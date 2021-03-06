package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "Product", description = "Product API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiOperation(value = "Add a product")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful addition of a product"),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResourceCreated<Long> createProduct(@PathVariable("storeId") Long storeId,
                                               @ApiParam(name = "product",
                                                   value="JSON data of the product to be created",
                                                   required = true)
                                               @Validated @RequestBody Product product) {
        product.setStoreId(storeId);
        return new ResourceCreated<>(productService.createProduct(product));
    }

    @ApiOperation(value = "Gets products of a store ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the list of products"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 404, message = "The store was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.GET)
    public List<Product> listProducts(Pageable pageable,
                                      @PathVariable("storeId") Long storeId) {
        return productService.findProducts(pageable, storeId);
    }

    @ApiOperation(value = "Get a product")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved the product"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 404, message = "The store or the product was not found")
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page", defaultValue = "20"),
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{storeId:\\d+}/{productId:\\d+}", method = RequestMethod.GET)
    public Optional<Product> getProduct(@PathVariable("storeId") Long storeId,
                                        @PathVariable(value = "productId") Long productId) {
        return productService.findProductByStoreIdAndProductId(storeId, productId);


    }

}
