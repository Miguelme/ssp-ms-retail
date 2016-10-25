package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class StockControllerTest extends BaseUtilIntegrationTest{

    @Test
    @FlywayTest
    public void testCreateStock() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> stockResponse = addStock(stockValidRequestFile, store.getId() , product.getId());

            assertEquals(String.format("HTTP Status code incorrect %s", stockResponse.getBody()), HttpStatus.CREATED, stockResponse.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    @FlywayTest
    public void testCreateStockStoreNotFound() {
        ResponseEntity<String> stockResponse = addStock(stockValidRequestFile, 4L, 6L);
        assertEquals(String.format("HTTP Status code incorrect %s", stockResponse.getBody()), HttpStatus.NOT_FOUND, stockResponse.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateStockProductNotFound() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> stockResponse = addStock(stockValidRequestFile, store.getId() , 5L);

            assertEquals(String.format("HTTP Status code incorrect %s", stockResponse.getBody()), HttpStatus.NOT_FOUND, stockResponse.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateStockNegativeCount() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> stockResponse = addStock(stockInvalidRequestFile, store.getId() , product.getId());

            assertEquals(String.format("HTTP Status code incorrect %s", stockResponse.getBody()), HttpStatus.PRECONDITION_FAILED, stockResponse.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
