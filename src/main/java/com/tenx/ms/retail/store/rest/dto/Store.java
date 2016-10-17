package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel("Store")
@Data
public class Store {

    @ApiModelProperty(value = "Store Id", required = true, readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Name of the store", required = true)
    @NotBlank
    private String name;

}
