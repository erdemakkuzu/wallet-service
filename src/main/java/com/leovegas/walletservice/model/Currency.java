package com.leovegas.walletservice.model;

public enum Currency {
    EURO("EU"),
    US_DOLLAR("USD"),
    SWEDISH_KRONA("SEK"),
    TURKISH_LIRA("TL");

    private final String currencyCode;

    Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
