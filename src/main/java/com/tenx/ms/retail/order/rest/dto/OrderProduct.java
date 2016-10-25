package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class OrderProduct {

    @ApiModelProperty(value = "Product Id", required = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(value = "Product Count", required = true, example="20")
    @NotNull
    @Min(0)
    private Long count;
}
