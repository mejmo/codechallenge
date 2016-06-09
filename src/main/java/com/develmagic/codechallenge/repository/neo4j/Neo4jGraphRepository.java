package com.develmagic.codechallenge.repository.neo4j;

import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Profile("neo4j")
@Qualifier("transactionRepository")
public interface Neo4jGraphRepository extends GraphRepository<Transaction>, TransactionRepository {

    Transaction findByTransactionId(Long transactionId);
    List<Transaction> findByType(String name);

    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() WITH n,r LIMIT 50000 DELETE n,r RETURN count(n) as deletedNodesCount")
    void clear();

    @Query(value="" +
            "MATCH (root:Transaction)-[BELONGS_TO*]-(child)\n" +
            "WHERE root.transactionId = {transactionId} WITH sum(child.amount)+root.amount AS total RETURN total;")
    Double makeSum(@Param("transactionId") Long transactionId);


}

