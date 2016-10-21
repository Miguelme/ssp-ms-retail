package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "Store", description = "Store API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Create a Store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful creation of a store"),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> createStore(@ApiParam(name = "store",
                                            value="JSON data of the store to be created",
                                            required = true)
                                            @Validated @RequestBody Store store) {
        return new ResourceCreated<>(storeService.createStore(store));
    }


    @ApiOperation(value = "List Stores")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The stores were successfully retrieved"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> listStores(@RequestParam(value = "name", required = false) Optional<String> name) {
        return storeService.findAllStores(name);
    }

    @ApiOperation(value = "Get Store By Id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The stores were successfully retrieved"),
        @ApiResponse(code = 404, message = "Store not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.GET)
    public Store findStoreById(@PathVariable("storeId") Long storeId) {
        return storeService.findStoreById(storeId).get();
    }

    @ApiOperation(value = "Delete Store By Id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The stores were successfully retrieved"),
        @ApiResponse(code = 404, message = "Store not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.DELETE)
    public void deleteStoreById(@PathVariable("storeId") Long storeId) {
        storeService.deleteStoreById(storeId);
    }
}
