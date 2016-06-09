package com.develmagic.codechallenge.repository.treemap;

import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.repository.TransactionRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/31/16.
 */
@Component
@Qualifier("transactionRepository")
@Profile("treemap")
public class TreemapTransactionRepository implements TransactionRepository {

    /**
     * We do not expose the data structure from the storage, but we provide a proxy object.
     * Parent depth equals to one, since we do not need it deeper.
     * @param transaction
     * @return
     */
    private Transaction createProxy(Transaction transaction) {
        Transaction proxy = new Transaction();
        proxy.setAmount(transaction.getAmount());
        proxy.setType(transaction.getType());
        proxy.setTransactionId(transaction.getTransactionId());
        proxy.setParent(transaction.getParent());
        return proxy;
    }


    public <S extends Transaction> Iterable<S> save(Iterable<S> iterable) {
        iterable.forEach(transaction -> save(transaction));
        return iterable;
    }

    @Autowired
    private TransactionStorage transactionStorage;

    public Transaction findOne(Long aLong) {
        Transaction t = transactionStorage.getNode(aLong);
        return t == null ? t : createProxy(transactionStorage.getNode(aLong));
    }

    public boolean exists(Long aLong) {
        return transactionStorage.exists(aLong);
    }

    public Iterable<Transaction> findAll() {
        return transactionStorage.getAll();
    }

    public Iterable<Transaction> findAll(Iterable<Long> iterable) {
        throw new NotImplementedException("TBD");
    }

    public long count() {
        return transactionStorage.size();
    }

    public void delete(Long aLong) {
        transactionStorage.remove(aLong);
    }

    public void delete(Transaction transaction) {
        transactionStorage.remove(transaction.getTransactionId());
    }

    public void delete(Iterable<? extends Transaction> iterable) {
        iterable.forEach(transaction -> transactionStorage.remove(transaction.getTransactionId()));
    }

    public void deleteAll() {
        transactionStorage.clear();
    }

//    @Override
    public Transaction findByTransactionId(Long transactionId) {
        return findOne(transactionId);
    }

    @Override
    public List<Transaction> findByType(String name) {
        return transactionStorage.findByType(name);
    }

//    @Override
    public Double makeSum(Long transactionId) {
        return transactionStorage.getAllChildrenTransactions(transactionId)
                .stream()
                .mapToDouble(transaction -> transaction.getAmount())
                .sum();
    }

    @Override
    public void clear() {
        transactionStorage.clear();
    }

    @Override
    public Transaction save(Transaction transaction) {
        transactionStorage.store(transaction);
        return transaction;
    }


}
