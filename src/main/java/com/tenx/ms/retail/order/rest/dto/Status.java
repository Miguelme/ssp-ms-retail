package com.tenx.ms.retail.order.rest.dto;


import com.tenx.ms.commons.rest.BaseValueNameEnum;
import io.swagger.annotations.ApiModel;

@ApiModel
public enum Status implements BaseValueNameEnum<Status> {

    ORDERED(1, "ORDERED"),
    PACKING(2, "PACKING"),
    SHIPPED(3, "SHIPPED"),
    INVALID(0, "INVALID");


    private int value;
    private String label;

    Status(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toJson() {
        return label;
    }
    @Override
    public Status getInvalidEnum() {
        return INVALID;
    }

    @Override
    public int getValue() {
        return value;
    }
}
