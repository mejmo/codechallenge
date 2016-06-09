package com.develmagic.codechallenge.controller;

import com.develmagic.codechallenge.controller.dto.common.Result;
import com.develmagic.codechallenge.controller.dto.request.StoreTransactionRequest;
import com.develmagic.codechallenge.controller.dto.result.CreateTransactionResult;
import com.develmagic.codechallenge.controller.dto.result.GetSumResult;
import com.develmagic.codechallenge.controller.dto.result.GetTransactionResult;
import com.develmagic.codechallenge.controller.exceptions.CCStoreResourceException;
import com.develmagic.codechallenge.controller.helper.DTOHelper;
import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.controller.exceptions.CCResourceException;
import com.develmagic.codechallenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@RestController
@RequestMapping("/transactionservice")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DTOHelper dtoHelper;

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<GetTransactionResult> getTransaction(@PathVariable Long transactionId) {

        Transaction transaction = transactionService.findByTransactionId(transactionId);

        if (transaction == null)
            return new ResponseEntity<GetTransactionResult>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<GetTransactionResult>(dtoHelper.toDTO(transaction), HttpStatus.OK);

    }

    @RequestMapping(value = "/transaction/{transactionId}", method = RequestMethod.PUT)
    public ResponseEntity<CreateTransactionResult> storeTransaction(
            @PathVariable Long transactionId,
            @RequestBody(required = true) StoreTransactionRequest request) {

        if (transactionService.storeTransaction(transactionId, request) != null) {
            return new ResponseEntity<CreateTransactionResult>(new CreateTransactionResult(Result.ok), HttpStatus.OK);
        } else {
            throw new CCStoreResourceException("Cannot save the resource.");
        }

    }

    @RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<Long>> findByType(@PathVariable String type) {

        List<Long> list = transactionService.findIdsByType(type);
        return new ResponseEntity<List<Long>>(list, HttpStatus.OK);

    }

    @RequestMapping(value = "/sum/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<GetSumResult> makeSum(@PathVariable Long transactionId) {

        return new ResponseEntity<GetSumResult>(
                new GetSumResult(transactionService.makeSum(transactionId)),HttpStatus.OK);

    }

}
