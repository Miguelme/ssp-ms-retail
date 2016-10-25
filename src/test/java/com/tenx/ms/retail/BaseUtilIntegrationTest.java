package com.tenx.ms.retail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.BaseIntegrationTest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

import static org.aspectj.bridge.MessageUtil.fail;

public class BaseUtilIntegrationTest extends BaseIntegrationTest {

    protected static final String API_VERSION = "v1";

    protected final String STORE_REQUEST_API_URL = "%s/" + API_VERSION + "/stores";
    protected final String PRODUCT_REQUEST_API_URL = "%s/" + API_VERSION + "/products/%s";
    protected final String STOCK_REQUEST_API_URL = "%s/" + API_VERSION + "/stock/%s/%s";
    protected final String ORDER_REQUEST_API_URL = "%s/" + API_VERSION + "/orders/%s";

    @Value("classpath:store/store_valid_request.json")
    protected File storeValidRequestFile;

    @Value("classpath:store/store_invalid_request.json")
    protected File storeInvalidRequestFile;

    @Value("classpath:store/store_valid_get_response.json")
    protected File storeValidResponseFile;

    @Value("classpath:store/store_valid_get_by_name_response.json")
    protected File storeValidResponseByNameFile;

    @Value("classpath:product/product_valid_request.json")
    protected File productValidRequestFile;

    @Value("classpath:product/product_valid_get_by_response.json")
    protected File productValidResponseFile;

    @Value("classpath:product/product_name_blank_request.json")
    protected File productNameBlankRequestFile;

    @Value("classpath:product/product_not_name_request.json")
    protected File productNotNameRequestFile;

    @Value("classpath:product/product_invalid_sku_request.json")
    protected File productInvalidSKURequestFile;

    @Value("classpath:product/product_not_sku_request.json")
    protected File productNotSKURequestFile;

    @Value("classpath:product/product_invalid_price_request.json")
    protected File productInvalidPriceRequestFile;

    @Value("classpath:product/product_not_price_request.json")
    protected File productNotPriceRequestFile;

    @Value("classpath:stock/stock_valid_request.json")
    protected File stockValidRequestFile;

    @Value("classpath:stock/stock_invalid_request.json")
    protected File stockInvalidRequestFile;

    @Value("classpath:order/order_valid_request.json")
    protected File orderValidRequestFile;

    @Value("classpath:order/order_valid_response_request.json")
    protected File orderValidResponseFile;

    @Value("classpath:order/order_backordered_response.json")
    protected File orderBackorderedResponseFile;

    @Value("classpath:order/order_invalid_product_count_request.json")
    protected File orderInvalidProductCountRequestFile;

    @Value("classpath:order/order_invalid_phone_request.json")
    protected File orderInvalidPhoneRequestFile;

    @Value("classpath:order/order_invalid_last_name_request.json")
    protected File orderInvalidLastNameRequestFile;

    @Value("classpath:order/order_invalid_first_name_request.json")
    protected File orderInvalidFirstNameRequestFile;

    @Value("classpath:order/order_invalid_email_request.json")
    protected File orderInvalidEmailRequestFile;

    @Value("classpath:order/order_no_email_request.json")
    protected File orderNoEmailRequestFile;

    @Value("classpath:order/order_no_last_name_request.json")
    protected File orderNoLastNameRequestFile;

    @Value("classpath:order/order_no_first_name_request.json")
    protected File orderNoFirstNameRequestFile;

    @Value("classpath:order/order_no_phone_request.json")
    protected File orderNoPhoneRequestFile;

    @Value("classpath:order/order_no_product_count_request.json")
    protected File orderNoProductCountRequestFile;

    @Value("classpath:order/order_no_product_id_request.json")
    protected File orderNoProductIdRequestFile;

    @Value("classpath:order/order_no_product_list_request.json")
    protected File orderNoProductListRequestFile;

    @Value("classpath:order/order_empty_product_list_request.json")
    protected File orderEmptyProductListRequestFile;


    protected ResponseEntity<String> createStore(File file){
        try {
            String storeRequest = FileUtils.readFileToString(file);
            return getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()), storeRequest, HttpMethod.POST);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    protected ResponseEntity<String> getStores() {
        return getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) , null, HttpMethod.GET);
    }

    protected ResponseEntity<String> getStoresById(Long storeId) {
        return getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) + "/" + storeId, null, HttpMethod.GET);
    }

    protected ResponseEntity<String> getStoresByName(String storeName) {
        return getStringResponse(String.format(STORE_REQUEST_API_URL, getBasePath()) + "?name="+storeName, null, HttpMethod.GET);
    }

    protected ResponseEntity<String> addProduct(File file, Long storeId){
        try {
            String productRequest = FileUtils.readFileToString(file);
            return getStringResponse(String.format(PRODUCT_REQUEST_API_URL, getBasePath(), storeId), productRequest, HttpMethod.POST);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    protected ResponseEntity<String> getProducts(Long storeId) {
        return getStringResponse(String.format(PRODUCT_REQUEST_API_URL, getBasePath(), storeId) , null, HttpMethod.GET);
    }

    protected ResponseEntity<String> getProductById(Long storeId, Long productId) {
        return getStringResponse(String.format(PRODUCT_REQUEST_API_URL, getBasePath(), storeId) + "/" + productId , null, HttpMethod.GET);
    }

    protected ResponseEntity<String> addStock(File file, Long storeId, Long productId){
        try {
            String stockRequest = FileUtils.readFileToString(file);
            return getStringResponse(String.format(STOCK_REQUEST_API_URL, getBasePath(), storeId, productId), stockRequest, HttpMethod.POST);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    protected ResponseEntity<String> createOrder(File file, Long storeId){
        try {
            String orderRequest = FileUtils.readFileToString(file);
            return getStringResponse(String.format(ORDER_REQUEST_API_URL, getBasePath(), storeId), orderRequest, HttpMethod.POST);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    protected Long setValidStock() {
        try {
            ResponseEntity<String> storeResponse = createStore(storeValidRequestFile);
            ResourceCreated<Long> store = mapper.readValue(storeResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            ResponseEntity<String> productResponse = addProduct(productValidRequestFile, store.getId());
            ResourceCreated<Long> product = mapper.readValue(productResponse.getBody(), new TypeReference<ResourceCreated<Long>>() {
            });
            addStock(stockValidRequestFile, store.getId(), product.getId());
            return store.getId();
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }
}
