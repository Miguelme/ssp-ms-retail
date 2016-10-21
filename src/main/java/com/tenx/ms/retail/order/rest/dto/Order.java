package com.tenx.ms.retail.order.rest.dto;


import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel("Order")
@Data
public class Order {

    @ApiModelProperty(value =  "Order Id", readOnly = true)
    private Long orderId;

    @ApiModelProperty(value =  "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value =  "Order Date")
    private Date orderDate;

    @ApiModelProperty(value =  "Order Status")
    private Status status;

    @ApiModelProperty(value =  "Purchaser First Name", required = true, example = "Miguel")
    @Pattern(regexp = "^[\\p{Alpha}]{1,50}$")
    @NotBlank
    private String firstName;

    @ApiModelProperty(value =  "Purchaser Last Name", required = true, example = "Fagundez")
    @Pattern(regexp = "^[\\p{Alpha}]{1,50}$")
    @NotBlank
    private String lastName;

    @ApiModelProperty(value =  "Purchaser Email", required = true, example = "miguel@email.com")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(value =  "Purchaser Phone", required = true, example = "2134567890")
    @NotBlank
    @PhoneNumber
    private String phone;

    @ApiModelProperty(value = "Products", required = true)
    @Size(min = 1)
    @NotNull
    @Valid
    private List<OrderProduct> products;

    @ApiModelProperty(value = "Backordered products")
    private List<OrderProduct> backorderedProducts;

    @ApiModelProperty(value = "Sold products")
    private List<OrderProduct> soldProducts;


}
