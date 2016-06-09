package com.develmagic.codechallenge.controller.dto.result;

import com.develmagic.codechallenge.controller.dto.common.BaseDTO;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
public class GetSumResult extends BaseDTO {

    private Double sum;

    public GetSumResult(Double sum) {
        this.sum = sum;
    }


    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
