package com.tenx.ms.retail.product.rest.dto;


import com.tenx.ms.commons.validation.constraints.DollarAmount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel("Product")
@Data
public class Product {

    @ApiModelProperty(value =  "Product Id", readOnly = true)
    private Long productId;

    @ApiModelProperty(value =  "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value =  "Name of the product", required = true, example="Product Name")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Description of the product", example = "Product Description")
    private String description;

    @ApiModelProperty(value = "SKU of the product", required = true, example = "AAA123")
    @Pattern(regexp = "^[\\p{Alnum}]{5,10}$")
    @NotNull
    private String sku;

    @ApiModelProperty(value = "Product Price", required = true, example = "253.23")
    @NotNull
    @DollarAmount
    private BigDecimal price;

}
