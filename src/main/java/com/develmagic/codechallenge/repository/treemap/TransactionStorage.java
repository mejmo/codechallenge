package com.develmagic.codechallenge.repository.treemap;

import com.develmagic.codechallenge.domain.Transaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 6/1/16.
 */
@Component
@Scope("singleton")
public class TransactionStorage extends TreemapStorage<Long, Transaction> {

    private ConcurrentHashMap<String, Map<Long, Transaction>> typeIndices = new ConcurrentHashMap<>();

    public Transaction store(Transaction transaction) {
        addToTypeIndices(transaction);
        if (transaction.getParent() == null)
            this.addRootNode(transaction.getTransactionId(), transaction);
        else
            this.addSubNode(transaction.getParent().getTransactionId(), transaction.getTransactionId(), transaction);
        return transaction;
    }

    public List<Transaction> findByType(String name) {
        if (typeIndices.containsKey(name))
            return new ArrayList(typeIndices.get(name).values());
        else
            return new ArrayList();
    }

    private void addToTypeIndices(Transaction transaction) {
        if (!typeIndices.containsKey(transaction.getType()))
            typeIndices.put(transaction.getType(), new HashMap());
        typeIndices.get(transaction.getType()).put(transaction.getTransactionId(), transaction);
    }

    public void clear() {
        super.clear();
        typeIndices.clear();
    }

    public List<Transaction> getAllChildrenTransactions(Long transactionId) {
        return super.getAllChildrenNodes(transactionId);
    }

}
