package com.develmagic.codechallenge.service;

import com.develmagic.codechallenge.controller.dto.request.StoreTransactionRequest;
import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.controller.exceptions.CCResourceNotFoundException;
import com.develmagic.codechallenge.controller.exceptions.CCStoreResourceException;
import com.develmagic.codechallenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/30/16.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findByTransactionId(Long transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);

        if (transaction != null) {
            return transaction;
        } else {
            throw new CCResourceNotFoundException("Resource not found");
        }

    }

    public List<Long> findIdsByType(String type) {
        return transactionRepository.findByType(type)
                .stream()
                .map(transaction -> transaction.getTransactionId())
                .collect(Collectors.toList());
    }

    public double makeSum(Long transactionId) {

        if (transactionRepository.findByTransactionId(transactionId) == null)
            throw new CCResourceNotFoundException();

        if (transactionRepository.makeSum(transactionId) == null)
            return transactionRepository.findByTransactionId(transactionId).getAmount();

        return transactionRepository.makeSum(transactionId);
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findByTransactionId(id);
    }

    @Transactional
    public Transaction storeTransaction(Long transactionId, StoreTransactionRequest request) {

        Transaction transaction = transactionRepository.findByTransactionId(transactionId);

        if (transaction == null)
            transaction = new Transaction(transactionId);

        if (request.getParentId() != null) {

            if (request.getParentId().equals(transactionId))
                throw new CCStoreResourceException("ParentId and Transaction id must be non-null");

            Transaction transaction1 = transactionRepository.findByTransactionId(request.getParentId());

            if (transaction1 == null)
                throw new CCStoreResourceException("Non-existing parent");

            transaction.setParent(transaction1);
        } else {
            transaction.setParent(null);
        }

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        return transactionRepository.save(transaction);

    }

    public void clear() {
        transactionRepository.clear();
    }
}
