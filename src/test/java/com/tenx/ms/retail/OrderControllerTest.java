package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import org.apache.commons.io.FileUtils;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
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
@SuppressWarnings("PMD")
public class OrderControllerTest extends BaseUtilIntegrationTest{

    @Test
    @FlywayTest
    public void testCreateOrder() {
        try {

            String orderValidResponse = FileUtils.readFileToString(orderValidResponseFile);
            Long storeId = setValidStock();
            ResponseEntity<String> response = createOrder(orderValidRequestFile, storeId);
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.CREATED, response.getStatusCode());
            JSONAssert.assertEquals(orderValidResponse, response.getBody(), false);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoStore() {
        ResponseEntity<String> response = createOrder(orderValidRequestFile, 30L);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoProduct() {
        try {
            String orderBackorderedResponse = FileUtils.readFileToString(orderBackorderedResponseFile);
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            ResponseEntity<String> response = createOrder(orderValidRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.CREATED, response.getStatusCode());
            JSONAssert.assertEquals(orderBackorderedResponse, response.getBody(), false);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

    }

    @Test
    @FlywayTest
    public void testCreateOrderNoStock() {
        try {
            String orderBackorderedResponse = FileUtils.readFileToString(orderBackorderedResponseFile);
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {});
            ResponseEntity<String> response = createOrder(orderValidRequestFile, store.getId());
            assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.CREATED, response.getStatusCode());
            JSONAssert.assertEquals(orderBackorderedResponse, response.getBody(), false);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidEmail() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderInvalidEmailRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidFirstName() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderInvalidFirstNameRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidLastName() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderInvalidLastNameRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidPhone() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderInvalidProductCountRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderInvalidProductCount() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderInvalidProductCountRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoEmail() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoEmailRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }


    @Test
    @FlywayTest
    public void testCreateOrderNoFirstName() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoFirstNameRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }


    @Test
    @FlywayTest
    public void testCreateOrderNoLastName() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoLastNameRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoPhone() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoPhoneRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoProductCount() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoProductCountRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoProductId() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoProductIdRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderNoProductList() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderNoProductListRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    @FlywayTest
    public void testCreateOrderEmptyProductList() {
        Long storeId = setValidStock();
        ResponseEntity<String> response = createOrder(orderEmptyProductListRequestFile, storeId);
        assertEquals(String.format("HTTP Status code incorrect %s", response.getBody()), HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }
}
