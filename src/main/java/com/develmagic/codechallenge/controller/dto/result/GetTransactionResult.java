package com.develmagic.codechallenge.controller.dto.result;

import com.develmagic.codechallenge.controller.dto.common.BaseDTO;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public class GetTransactionResult extends BaseDTO {

    private Double amount;
    private String type;
    private Long transactionId;
    private Long parentId;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
