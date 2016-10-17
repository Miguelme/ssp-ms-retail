package com.tenx.ms.retail.product.rest.dto;


import com.tenx.ms.commons.validation.constraints.DollarAmount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel("Product")
public @Data class Product {

    @ApiModelProperty(value =  "Product Id", readOnly = true)
    private Long productId;

    @ApiModelProperty(value =  "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value =  "Name of the product")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Description of the product")
    private String description;

    @ApiModelProperty(value = "SKU of the product")
    @Pattern(regexp = "^[\\p{Alnum}]{5,10}$")
    private String sku;

    @ApiModelProperty(value = "Product Price")
    @DollarAmount
    private BigDecimal price;

}
