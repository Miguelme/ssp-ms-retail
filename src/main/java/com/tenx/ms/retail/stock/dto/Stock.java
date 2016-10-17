package com.tenx.ms.retail.stock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class Stock {
    @ApiModelProperty(value = "Product Id")
    @Getter @Setter
    private Long productId;

    @ApiModelProperty(value = "Store Id")
    @Getter @Setter
    private Long storeId;

    @ApiModelProperty(value = "Product Count")
    @Getter @Setter
    private Long count;
}
