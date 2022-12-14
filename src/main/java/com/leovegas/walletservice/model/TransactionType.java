package com.leovegas.walletservice.model;

public enum TransactionType {
    DEBIT("DEBIT"),
    CRE("CREDIT"),
    TRAN("TRANSFER");

    private final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
