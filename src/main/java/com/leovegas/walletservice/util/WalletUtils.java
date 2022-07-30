package com.leovegas.walletservice.util;

import com.leovegas.walletservice.constant.FieldKeys;
import com.leovegas.walletservice.exception.InvalidCurrencyException;
import com.leovegas.walletservice.exception.NegativeBalanceException;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.model.CreateWalletRequest;
import com.leovegas.walletservice.model.Currency;
import com.leovegas.walletservice.model.PerformTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class WalletUtils {

    private static final Logger logger = LoggerFactory.getLogger(WalletUtils.class);

    public static void validateCreateWalletRequest(CreateWalletRequest createWalletRequest) {
        if (createWalletRequest.getBalance() < 0) {
            logger.error("Requested balance is a negative. Balance : " + createWalletRequest.getBalance());
            throw new NegativeBalanceException(createWalletRequest.getBalance());
        }
        if (createWalletRequest.getName().isEmpty() || createWalletRequest.getName().isBlank()) {
            logger.error("Field is null :" + FieldKeys.WALLET_NAME);
            throw new NullFieldException(FieldKeys.WALLET_NAME);
        }

        validateCurrency(createWalletRequest.getCurrency());
    }

    public static void validatePerformTransactionRequest(PerformTransactionRequest performTransactionRequest) {
        if (performTransactionRequest.getAmount() < 0) {
            logger.error("Requested amount is a negative. Amount : " + performTransactionRequest.getAmount());
            throw new NegativeBalanceException(performTransactionRequest.getAmount());
        }

        if (performTransactionRequest.getHashId().isEmpty() || performTransactionRequest.getHashId().isBlank()) {
            logger.error("Field is null :" + FieldKeys.HASH_ID);
            throw new NullFieldException(FieldKeys.HASH_ID);
        }

        validateCurrency(performTransactionRequest.getCurrency());
    }

    public static void validateCurrency(String currency) {
        if (currency == null) {
            logger.error("Field is null :" + FieldKeys.CURRENCY_CODE);
            throw new NullFieldException(FieldKeys.CURRENCY_CODE);
        }

        boolean validCurrency = Arrays.stream(Currency.values()).
                anyMatch(currencyValue -> currencyValue.getCurrencyCode().equals(currency));

        if (!validCurrency) {
            logger.error("Currency code is not valid : " + currency);
            throw new InvalidCurrencyException(currency);
        }
    }
}
