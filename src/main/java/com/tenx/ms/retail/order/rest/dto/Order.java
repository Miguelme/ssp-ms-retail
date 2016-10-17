package com.tenx.ms.retail.order.rest.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ApiModel
public @Data
class Order {

    private Long orderId;
    private Long storeId;
    private LocalDateTime orderDate;
    private Status status;
    private ArrayList<String> products;
    @Pattern(regexp = "^[\\p{Alpha}]{1,50}$")
    @NotBlank
    private String firstName;
    @Pattern(regexp = "^[\\p{Alpha}]{1,50}$")
    @NotBlank
    private String lastName;
    private String email;
    private String phone;

}
