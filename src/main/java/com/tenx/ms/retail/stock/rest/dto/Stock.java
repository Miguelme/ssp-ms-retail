package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("Stock")
@Data
public class Stock {
    @ApiModelProperty(value = "Product Id", readOnly = true)
    private Long productId;

    @ApiModelProperty(value = "Store Id", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Product Count", required = true, example = "20")
    @NotNull
    @Min(0)
    private Long count;
}
