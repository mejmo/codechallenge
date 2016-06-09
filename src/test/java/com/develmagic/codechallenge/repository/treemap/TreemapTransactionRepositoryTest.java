package com.develmagic.codechallenge.repository.treemap;

import com.develmagic.codechallenge.TreemapCodeChallengeApplication;
import com.develmagic.codechallenge.domain.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


/**
 * CodeChallenge - 2016 (c) MartinFormanko 5/31/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TreemapCodeChallengeApplication.class)
@ActiveProfiles({"treemap"})
public class TreemapTransactionRepositoryTest {

    @InjectMocks
    private TreemapTransactionRepository transactionRepository;

    @Mock
    private TransactionStorage treemapStorage;

    private Transaction transaction;
    private Transaction transaction0;
    private Transaction transaction1;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        transaction = new Transaction();
        transaction.setParent(null);
        transaction.setType("car");
        transaction.setTransactionId(100L);
        transaction.setAmount(1d);

        transaction0 = new Transaction();
        transaction0.setParent(transaction);
        transaction0.setType("plane");
        transaction0.setTransactionId(200L);
        transaction0.setAmount(2d);

        transaction1 = new Transaction();
        transaction1.setType("car");
        transaction1.setTransactionId(300L);
        transaction1.setAmount(3d);

        Transaction transaction2 = new Transaction();
        transaction2.setParent(transaction0);
        transaction2.setType("boat");
        transaction2.setTransactionId(400L);
        transaction2.setAmount(4d);

        List<Transaction> set = new ArrayList<>();
        set.add(transaction);
        set.add(transaction0);
        set.add(transaction1);
        set.add(transaction2);

        List<Transaction> set2 = new ArrayList<>();
        set2.add(transaction0);
        set2.add(transaction1);

        Mockito.when(treemapStorage.getNode(transaction.getTransactionId())).thenReturn(transaction);
        Mockito.when(treemapStorage.getNode(transaction0.getTransactionId())).thenReturn(transaction0);
        Mockito.when(treemapStorage.getNode(transaction1.getTransactionId())).thenReturn(transaction1);
        Mockito.when(treemapStorage.getNode(transaction2.getTransactionId())).thenReturn(transaction2);
        Mockito.when(treemapStorage.findByType("car")).thenReturn(set2);
//        Mockito.when(treemapStorage.getAllChildrenNodes(100L)).thenReturn(set);
        Mockito.when(treemapStorage.getAllChildrenTransactions(100L)).thenReturn(set);
        Mockito.when(treemapStorage.getAll()).thenReturn(set);

    }

    @After
    public void clear() {
        treemapStorage.clear();
    }

    @Test
    public void testFindByTransactionId() {

        Transaction t0 = transactionRepository.findByTransactionId(100L);
        Transaction t1 = transactionRepository.findByTransactionId(200L);

        assertThat(t0.getAmount(), equalTo(transaction.getAmount()));
        assertThat(t0.getType(), equalTo(transaction.getType()));
        assertThat(t0.getTransactionId(), equalTo(transaction.getTransactionId()));

        assertThat(t1.getAmount(), equalTo(transaction0.getAmount()));
        assertThat(t1.getType(), equalTo(transaction0.getType()));
        assertThat(t1.getTransactionId(), equalTo(transaction0.getTransactionId()));
        assertThat(t1.getParent().getTransactionId(), equalTo(transaction.getTransactionId()));

    }

    @Test
    public void testFindByType() {

        List<Transaction> reps = transactionRepository.findByType("car");
        assertThat(reps.size(), equalTo(2));
        assertThat(reps.get(0).getTransactionId(), equalTo(transaction0.getTransactionId()));
        assertThat(reps.get(1).getTransactionId(), equalTo(transaction1.getTransactionId()));

    }

    @Test
    public void testmakeSum() {

        Double sum = transactionRepository.makeSum(100L);
        assertThat(sum, equalTo(10d));

    }

}
