package com.leovegas.walletservice.util;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Transaction;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.model.*;

import java.util.*;

public class MapperUtils {

    public static CreateWalletResponse mapToCreateWalletResponse(Wallet wallet, String playerName) {
        CreateWalletResponse createWalletResponse = new CreateWalletResponse();
        createWalletResponse.setId(wallet.getId());
        createWalletResponse.setName(wallet.getName());
        createWalletResponse.setBalance(wallet.getBalance());
        createWalletResponse.setOwner(playerName);
        createWalletResponse.setCreatedDate(wallet.getCreatedDate());
        createWalletResponse.setCurrency(wallet.getCurrency());

        return createWalletResponse;
    }

    public static GetPlayerResponse mapToGetPlayerResponse(Player player) {
        GetPlayerResponse getPlayerResponse = populateGetPlayerResponse(player);

        List<GetWalletResponse> walletResponseList = new ArrayList<>();
        Map<String, Double> totalBalanceMap = new HashMap<>();
        getPlayerResponse.setTotalBalanceList(new ArrayList<>());

        player.getWalletList().forEach(wallet -> {
            GetWalletResponse walletResponse = populateGetWalletResponse(wallet);
            addToTotalBalanceMap(totalBalanceMap, wallet);
            walletResponseList.add(walletResponse);
        });

        for (Map.Entry<String, Double> entry : totalBalanceMap.entrySet()) {
            Balance balance = new Balance(entry.getKey(), entry.getValue());
            getPlayerResponse.getTotalBalanceList().add(balance);
        }
        getPlayerResponse.setWalletList(walletResponseList);

        return getPlayerResponse;

    }

    private static GetPlayerResponse populateGetPlayerResponse(Player player) {
        GetPlayerResponse getPlayerResponse = new GetPlayerResponse();
        getPlayerResponse.setId(player.getId());
        getPlayerResponse.setName(player.getName());
        getPlayerResponse.setFirstName(player.getFirstName());
        getPlayerResponse.setLastName(player.getLastName());

        return getPlayerResponse;
    }

    private static GetWalletResponse populateGetWalletResponse(Wallet wallet) {
        GetWalletResponse walletResponse = new GetWalletResponse();
        walletResponse.setId(wallet.getId());
        walletResponse.setName(wallet.getName());
        walletResponse.setBalance(wallet.getBalance());
        walletResponse.setCurrency(wallet.getCurrency());
        walletResponse.setCreateDate(wallet.getCreatedDate());

        return walletResponse;
    }

    private static void addToTotalBalanceMap(Map<String, Double> totalBalanceMap,
                                             Wallet wallet) {
        totalBalanceMap.merge(wallet.getCurrency(), wallet.getBalance(), Double::sum);
    }

    public static TransactionResponse mapToTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setHashId(transaction.getHashId());
        transactionResponse.setType(transaction.getType());
        transactionResponse.setAmount(transaction.getAmount());
        transactionResponse.setCurrency(transaction.getCurrency());
        transactionResponse.setDate(transaction.getDate());
        transactionResponse.setNote(transaction.getNote());
        return transactionResponse;
    }

    public static Transaction mapToTransactionEntity(PerformTransactionRequest performTransactionRequest,
                                                     Wallet wallet,
                                                     TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setCurrency(performTransactionRequest.getCurrency());
        transaction.setAmount(performTransactionRequest.getAmount());
        transaction.setDate(new Date());
        transaction.setWallet(wallet);
        transaction.setHashId(performTransactionRequest.getHashId());
        transaction.setNote(performTransactionRequest.getNote());
        transaction.setType(transactionType.getTransactionType());

        return transaction;
    }

    public static PerformTransactionResponse toPerformTransactionResponse(Wallet wallet,
                                                                          PerformTransactionRequest performTransactionRequest) {
        PerformTransactionResponse performTransactionResponse = new PerformTransactionResponse();
        performTransactionResponse.setWalletId(wallet.getId());
        performTransactionResponse.setCurrentBalance(wallet.getBalance());
        performTransactionResponse.setCurrency(performTransactionRequest.getCurrency());
        performTransactionResponse.setHashId(performTransactionRequest.getHashId());

        return performTransactionResponse;
    }
}
