package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SuppressWarnings("PMD")
public class ProductControllerTest extends BaseUtilIntegrationTest {

    @Test
    @FlywayTest
    public void testCreateProduct() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productValidRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.CREATED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testListOfProducts(){
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            addProduct(productValidRequestFile, store.getId());
            addProduct(productValidRequestFile, store.getId());
            ResponseEntity<String> response = getProducts(store.getId());
            List<Store> products = mapper.readValue(response.getBody(), new TypeReference<List<Product>>() {});
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
            assertEquals("The number of product is incorrect", 2, products.size());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testGetProduct(){

        try {
            String productValidResponse = FileUtils.readFileToString(productValidResponseFile);

            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });

            ResponseEntity<String> response = getProductById(store.getId(), product.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
            JSONAssert.assertEquals(productValidResponse, response.getBody(), false);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductStoreNotFound(){
        ResponseEntity<String> response = addProduct(productValidRequestFile, 20L);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testGetStoreNotFound(){
        ResponseEntity<String> response = getProducts(20L);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testGetProductNotFound(){
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            addProduct(productValidResponseFile, store.getId());
            ResponseEntity<String> response = getProductById(store.getId(), 20L);

            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.NOT_FOUND, response.getStatusCode());
        } catch (IOException e){
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductNameBlank() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productNameBlankRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductInvalidSKU() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productInvalidSKURequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductInvalidPrice() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productInvalidPriceRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductNotName() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productNotNameRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductNotSKU() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productNotSKURequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateProductNotPrice() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> response = addProduct(productNotPriceRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
