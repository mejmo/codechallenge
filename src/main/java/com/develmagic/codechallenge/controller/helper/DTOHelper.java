package com.develmagic.codechallenge.controller.helper;

import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.controller.dto.result.GetTransactionResult;
import org.springframework.stereotype.Component;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@Component
public class DTOHelper {

    public GetTransactionResult toDTO(Transaction transaction) {
        return DTOBuilder.createBuilder().newTransactionDTO()
                .withAmount(transaction.getAmount())
                .withParentId(transaction.getParent() == null ? null : transaction.getParent().getTransactionId())
                .withTransactionId(transaction.getTransactionId())
                .withType(transaction.getType())
                .getGetTransactionResult();

    }

}
