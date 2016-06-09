package com.develmagic.codechallenge.domain;


import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class Transaction {

    public Long getTransactionId() {
        return transactionId;
    }

    @GraphId
    private Long id;

    private Long transactionId;

    private double amount;

    private String type;

    public Transaction() {}

    public Transaction(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Relationship(type="BELONGS_TO", direction = Relationship.OUTGOING  )
    private Transaction parent;

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transaction getParent() {
        return parent;
    }

    public void setParent(Transaction parent) {
        this.parent = parent;
    }

}