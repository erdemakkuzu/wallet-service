package com.leovegas.walletservice.util;

import com.leovegas.walletservice.constans.FieldKeys;
import com.leovegas.walletservice.exception.InvalidCurrencyException;
import com.leovegas.walletservice.exception.NegativeBalanceException;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.model.CreateWalletRequest;
import com.leovegas.walletservice.model.Currency;

import java.util.Arrays;

public class WalletUtils {
    public static String mainWalletName = "Main Wallet";

    public static void validateCreateWalletRequest(CreateWalletRequest createWalletRequest) {
        if(createWalletRequest.getBalance() < 0){
            throw new NegativeBalanceException(createWalletRequest.getBalance());
        }
        if(createWalletRequest.getName().isEmpty() || createWalletRequest.getName().isBlank()){
            throw new NullFieldException(FieldKeys.WALLET_NAME);
        }

        if(createWalletRequest.getCurrency() == null){
            throw new NullFieldException(FieldKeys.CURRENCY_CODE);
        }

        boolean validCurrency = Arrays.stream(Currency.values()).
                anyMatch(currency -> currency.getCurrencyCode().equals(createWalletRequest.getCurrency()));

        if(!validCurrency){
            throw new InvalidCurrencyException(createWalletRequest.getCurrency());
        }
    }
}
