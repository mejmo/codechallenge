package com.develmagic.codechallenge.integration;

import com.develmagic.codechallenge.controller.dto.request.StoreTransactionRequest;
import com.develmagic.codechallenge.domain.Transaction;
import com.develmagic.codechallenge.service.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * CodeChallenge - 2016 (c) MartinFormanko 6/1/16.
 */
@WebIntegrationTest({"server.port=0", "management.port=0"})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransactionService transactionService;

    @Before
    /**
     * Do not run tests simultaneously
     */
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        StoreTransactionRequest t100 = new StoreTransactionRequest();
        t100.setAmount(100L);
        t100.setType("car");
        transactionService.storeTransaction(100L, t100);

        StoreTransactionRequest t101 = new StoreTransactionRequest();
        t101.setAmount(101L);
        t101.setType("food");
        t101.setParentId(100L);
        transactionService.storeTransaction(101L, t101);

        StoreTransactionRequest t102 = new StoreTransactionRequest();
        t102.setAmount(102L);
        t102.setType("books");
        t102.setParentId(100L);
        transactionService.storeTransaction(102L, t102);

        StoreTransactionRequest t103 = new StoreTransactionRequest();
        t103.setAmount(103L);
        t103.setType("car");
        t103.setParentId(102L);
        transactionService.storeTransaction(103L, t103);

        StoreTransactionRequest t104 = new StoreTransactionRequest();
        t104.setAmount(104L);
        t104.setType("boat");
        transactionService.storeTransaction(104L, t104);

    }

    @After
    public void clear() throws Exception {
        transactionService.clear();
    }

    @Test
    public void getTransaction() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/transaction/101")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is((double)101)))
                .andExpect(jsonPath("$.type", is("food")))
                .andExpect(jsonPath("$.transactionId", is(101)))
                .andExpect(jsonPath("$.parentId", is(100)));

        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/transaction/111")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getSum() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/sum/100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(406d)));

        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/sum/104")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is(104d)));
    }

    @Test
    public void getTypes() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/types/ooo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        mvc.perform(MockMvcRequestBuilders.get("/transactionservice/types/car")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(100)))
                .andExpect(jsonPath("$[1]", is(103)));

    }

    @Test
    public void putTransaction() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/transactionservice/transaction/200")
                .content("{ \"amount\": 5000, \"type\": \"type1\"}".getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("ok")));

        mvc.perform(MockMvcRequestBuilders.put("/transactionservice/transaction/201")
                .content("{ \"amount\": 5000, \"type\": \"type2\", \"parentId\": 200}".getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("ok")));

        mvc.perform(MockMvcRequestBuilders.put("/transactionservice/transaction/2000")
                .content("{ \"amount\": 5000, \"type\": \"type2\", \"parentId\": 2000}".getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        Transaction t = transactionService.getTransaction((long)201);
        assertThat("Checking amount", t.getAmount(), is((double)5000));
        assertThat("Checking type", t.getType(), is("type2"));
        assertThat("Checking id", t.getTransactionId(), is((long)201));
        assertThat("Checking parent", t.getParent().getTransactionId(), is((long)200));

    }

}
