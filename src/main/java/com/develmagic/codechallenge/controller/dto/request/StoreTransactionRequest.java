package com.develmagic.codechallenge.controller.dto.request;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public class StoreTransactionRequest {

    private Long amount;
    private String type;
    private Long parentId;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
