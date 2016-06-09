package com.develmagic.codechallenge.controller.helper;

import com.develmagic.codechallenge.controller.dto.result.GetTransactionResult;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public class DTOBuilder {

    private GetTransactionResult getTransactionResult;

    public DTOBuilder withAmount(Double amount) {
        getGetTransactionResult().setAmount(amount);
        return this;
    }

    public DTOBuilder withType(String type) {
        getGetTransactionResult().setType(type);
        return this;
    }

    public DTOBuilder withParentId(Long parentId) {
        getGetTransactionResult().setParentId(parentId);
        return this;
    }

    public DTOBuilder withTransactionId(Long transactionId) {
        getGetTransactionResult().setTransactionId(transactionId);
        return this;
    }

    public static DTOBuilder createBuilder() {
        return new DTOBuilder();
    }

    public DTOBuilder newTransactionDTO() {
        getTransactionResult = new GetTransactionResult();
        return this;
    }

    public GetTransactionResult getGetTransactionResult() {
        return getTransactionResult;
    }

}
