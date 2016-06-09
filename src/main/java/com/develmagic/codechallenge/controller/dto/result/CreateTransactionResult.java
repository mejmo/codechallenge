package com.develmagic.codechallenge.controller.dto.result;

import com.develmagic.codechallenge.controller.dto.common.BaseDTO;
import com.develmagic.codechallenge.controller.dto.common.Result;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public class CreateTransactionResult extends BaseDTO {
    private Result status;

    public CreateTransactionResult(Result status) {
        this.status = status;
    }

    public Result getStatus() {
        return status;
    }

    public void setStatus(Result status) {
        this.status = status;
    }
}
