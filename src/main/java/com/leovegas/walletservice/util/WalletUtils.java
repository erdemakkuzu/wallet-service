package com.leovegas.walletservice.util;

import com.leovegas.walletservice.constant.FieldKeys;
import com.leovegas.walletservice.exception.InvalidCurrencyException;
import com.leovegas.walletservice.exception.NegativeBalanceException;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.model.CreateWalletRequest;
import com.leovegas.walletservice.model.Currency;
import com.leovegas.walletservice.model.PerformTransactionRequest;

import java.util.Arrays;

public class WalletUtils {

    public static void validateCreateWalletRequest(CreateWalletRequest createWalletRequest) {
        if (createWalletRequest.getBalance() < 0) {
            throw new NegativeBalanceException(createWalletRequest.getBalance());
        }
        if (createWalletRequest.getName().isEmpty() || createWalletRequest.getName().isBlank()) {
            throw new NullFieldException(FieldKeys.WALLET_NAME);
        }

        validateCurrency(createWalletRequest.getCurrency());
    }

    public static void validatePerformTransactionRequest(PerformTransactionRequest performTransactionRequest) {
        if (performTransactionRequest.getAmount() < 0) {
            throw new NegativeBalanceException(performTransactionRequest.getAmount());
        }

        if (performTransactionRequest.getHashId().isEmpty() || performTransactionRequest.getHashId().isBlank()) {
            throw new NullFieldException(FieldKeys.HASH_ID);
        }

        validateCurrency(performTransactionRequest.getCurrency());
    }

    public static void validateCurrency(String currency) {
        if (currency == null) {
            throw new NullFieldException(FieldKeys.CURRENCY_CODE);
        }

        boolean validCurrency = Arrays.stream(Currency.values()).
                anyMatch(currencyValue -> currencyValue.getCurrencyCode().equals(currency));

        if (!validCurrency) {
            throw new InvalidCurrencyException(currency);
        }
    }
}
