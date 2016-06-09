package com.develmagic.codechallenge.repository.treemap;

import com.develmagic.codechallenge.Neo4jCodeChallengeApplication;
import com.develmagic.codechallenge.TreemapCodeChallengeApplication;
import com.develmagic.codechallenge.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/31/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TreemapCodeChallengeApplication.class)
@ActiveProfiles({"treemap"})
public class TransactionStorageTest {

    private TransactionStorage transactionStorage;

    @Autowired
    Environment env;

    @Before
    public void initStorage() {
        Transaction t100 = new Transaction();
        t100.setAmount(100);
        t100.setType("car");
        t100.setTransactionId((long)100);

        Transaction t101 = new Transaction();
        t101.setAmount(101);
        t101.setType("food");
        t101.setTransactionId((long)101);
        t101.setParent(t100);

        Transaction t102 = new Transaction();
        t102.setAmount(102);
        t102.setType("books");
        t102.setTransactionId((long)102);
        t102.setParent(t100);

        Transaction t103 = new Transaction();
        t103.setAmount(103);
        t103.setType("car");
        t103.setTransactionId((long)103);
        t103.setParent(t102);

        transactionStorage = new TransactionStorage();
        transactionStorage.store(t100);
        transactionStorage.store(t101);
        transactionStorage.store(t102);
        transactionStorage.store(t103);
    }

    @Test
    public void testStorageBasic() {

        assertThat("Transaction exists", transactionStorage.exists((long) 100));

        Transaction t = transactionStorage.getNode((long) 103);
        assertThat("Get amount", t.getAmount(), equalTo((double) 103));
        assertThat("Get type", t.getType(), equalTo("car"));
        assertThat("Get parent", t.getParent().getTransactionId(), equalTo((long) 102));

    }

    @Test
    public void testStorageSum() {

        assertThat("Make the sum of the children nodes", transactionStorage.getAllChildrenTransactions((long) 100)
                .stream()
                .mapToDouble(transaction -> transaction.getAmount())
                .sum(), equalTo((double) 406));

    }

    @Test
    public void testStorageFindType() {

        assertThat("Get all types size", transactionStorage.findByType("car").size(), equalTo(2));
        assertThat(transactionStorage.findByType("car").get(0).getType(), equalTo("car"));
        assertThat(transactionStorage.findByType("car").get(1).getType(), equalTo("car"));

    }

}
