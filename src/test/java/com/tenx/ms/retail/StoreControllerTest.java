package com.tenx.ms.retail;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.apache.commons.io.FileUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import java.io.IOException;
import java.util.List;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {RetailServiceApp.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@ActiveProfiles(Profiles.TEST_NOAUTH)
@SuppressWarnings("PMD")
public class StoreControllerTest extends BaseUtilIntegrationTest {

    @Test
    @FlywayTest
    public void testCreateStore() {
        try {
            ResponseEntity<String> response = createStore(storeValidRequestFile);
            ResourceCreated<Long> responseBody = mapper.readValue(response.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.CREATED, response.getStatusCode());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testGetStores(){
        try {
            createStore(storeValidRequestFile);
            createStore(storeValidRequestFile);
            ResponseEntity<String> response = getStores();
            List<Store> stores = mapper.readValue(response.getBody(), new TypeReference<List<Store>>() {});
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
            assertEquals("The number of stores is incorrect", 2, stores.size());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }


    @Test
    @FlywayTest
    public void testGetStore(){
        Long storeId = 1L;
        try {
            String storeValidResponse = FileUtils.readFileToString(storeValidResponseFile);
            createStore(storeValidRequestFile);
            ResponseEntity<String> response = getStoresById(storeId);
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
            JSONAssert.assertEquals(storeValidResponse, response.getBody(), false);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateStoreNoName(){
        ResponseEntity<String> response = createStore(storeInvalidRequestFile);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testGetStoreNotFound(){
        ResponseEntity<String> response = getStoresById(1L);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testGetStoreByNameNotFound(){
        ResponseEntity<String> response = getStoresByName("store1");
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(response.getBody(), "[]", false);
    }

    @Test
    @FlywayTest
    public void testGetStoreByName(){
        try {
            String storeValidResponseByName = FileUtils.readFileToString(storeValidResponseByNameFile);
            createStore(storeValidRequestFile);
            ResponseEntity<String> response = getStoresByName("store1");
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.OK, response.getStatusCode());
            JSONAssert.assertEquals(storeValidResponseByName, response.getBody(), false);
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testDeleteStore(){
        try {

            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            addStock(stockValidRequestFile, store.getId(), product.getId());
            ResponseEntity<String> responseDelete = getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) + "/" + store.getId() , null, HttpMethod.DELETE);
            assertEquals(String.format("HTTP Status code incorrect %s", responseDelete.getBody()), HttpStatus.OK, responseDelete.getStatusCode());
            ResponseEntity<String> responseGet = getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) + "/" + store.getId() , null, HttpMethod.GET);
            assertEquals(String.format("HTTP Status code incorrect %s", responseGet.getBody()), HttpStatus.NOT_FOUND, responseGet.getStatusCode());
            ResponseEntity<String> responseProduct = getStringResponse(String.format(PRODUCT_REQUEST_API_URL, getBasePath(), product.getId()) , null, HttpMethod.GET);
            assertEquals(String.format("HTTP Status code incorrect %s", responseProduct.getBody()), HttpStatus.NOT_FOUND, responseProduct.getStatusCode());

        } catch (IOException e) {
            fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testDeleteStoreNotFound(){

        ResponseEntity<String> responseDelete = getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) + "/" + 30L , null, HttpMethod.DELETE);
        assertEquals(String.format("HTTP Status code incorrect %s", responseDelete.getBody()), HttpStatus.NOT_FOUND, responseDelete.getStatusCode());
    }


}
