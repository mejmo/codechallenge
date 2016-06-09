package com.develmagic.codechallenge.repository;

import com.develmagic.codechallenge.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends TransactionRepositoryCustom {

    Transaction findByTransactionId(Long transactionId);
    List<Transaction> findByType(String name);
    void clear();
    Transaction save(Transaction t);
    Double makeSum(Long transactionId);

}

