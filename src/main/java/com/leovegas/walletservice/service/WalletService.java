package com.leovegas.walletservice.service;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Transaction;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.exception.*;
import com.leovegas.walletservice.model.*;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.TransactionRepository;
import com.leovegas.walletservice.repository.WalletRepository;
import com.leovegas.walletservice.util.MapperUtils;
import com.leovegas.walletservice.util.PlayerUtils;
import com.leovegas.walletservice.util.WalletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class WalletService {

    WalletRepository walletRepository;
    PlayerRepository playerRepository;
    TransactionRepository transactionRepository;

    @Autowired
    WalletService(WalletRepository walletRepository,
                  PlayerRepository playerRepository,
                  TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public CreateWalletResponse createWallet(String playerName,
                                             CreateWalletRequest createWalletRequest) {
        PlayerUtils.validatePlayerPathVariable(playerName);
        WalletUtils.validateCreateWalletRequest(createWalletRequest);

        Player player = playerRepository.findByName(playerName);

        if (player == null) {
            throw new PlayerNotFoundException(playerName);
        }

        Wallet wallet = saveWallet(createWalletRequest, player);

        return MapperUtils.mapToCreateWalletResponse(wallet, player.getName());
    }

    private Wallet saveWallet(CreateWalletRequest createWalletRequest,
                              Player player) {
        Wallet wallet = new Wallet();
        wallet.setName(createWalletRequest.getName());
        wallet.setBalance(createWalletRequest.getBalance() != null ? createWalletRequest.getBalance() : 0.0);
        wallet.setCurrency(createWalletRequest.getCurrency());
        wallet.setCreatedDate(new Date());
        wallet.setPlayer(player);

        return walletRepository.save(wallet);
    }

    @Transactional
    public PerformTransactionResponse performCredit(Long walletId,
                                                    PerformTransactionRequest performTransactionRequest) {
        Wallet wallet = validateTransactionRequestAndGetWallet(walletId, performTransactionRequest);

        Transaction transaction = populateTransactionFields(performTransactionRequest, wallet, TransactionType.CRE);

        wallet.setBalance(wallet.getBalance() + performTransactionRequest.getAmount());

        transactionRepository.save(transaction);

        return populateTransactionResponse(wallet, performTransactionRequest);
    }

    @Transactional
    public PerformTransactionResponse performDebit(Long walletId,
                                                   PerformTransactionRequest performTransactionRequest) {
        Wallet wallet = validateTransactionRequestAndGetWallet(walletId, performTransactionRequest);

        if (wallet.getBalance() < performTransactionRequest.getAmount()) {
            throw new NotEnoughBalanceException(wallet.getBalance().toString());
        }

        Transaction transaction = populateTransactionFields(performTransactionRequest, wallet, TransactionType.DEBIT);

        wallet.setBalance(wallet.getBalance() - performTransactionRequest.getAmount());

        transactionRepository.save(transaction);

        return populateTransactionResponse(wallet, performTransactionRequest);
    }

    private Wallet validateTransactionRequestAndGetWallet(Long walletId,
                                                          PerformTransactionRequest performTransactionRequest) {
        WalletUtils.validatePerformTransactionRequest(performTransactionRequest);

        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            throw new WalletNotFoundException(walletId);
        }

        Transaction existedTransaction = transactionRepository.findByHashId(performTransactionRequest.getHashId());

        if (existedTransaction != null) {
            throw new NonUniqueTransactionHashIdException(performTransactionRequest.getHashId());
        }

        if (!performTransactionRequest.getCurrency().equals(wallet.get().getCurrency())) {
            throw new CurrencyMisMatchException(performTransactionRequest.getCurrency());
        }
        return wallet.get();
    }

    private Transaction populateTransactionFields(PerformTransactionRequest performTransactionRequest,
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

    private PerformTransactionResponse populateTransactionResponse(Wallet wallet,
                                                                   PerformTransactionRequest performTransactionRequest) {
        PerformTransactionResponse performTransactionResponse = new PerformTransactionResponse();
        performTransactionResponse.setWalletId(wallet.getId());
        performTransactionResponse.setCurrentBalance(wallet.getBalance());
        performTransactionResponse.setCurrency(performTransactionRequest.getCurrency());
        performTransactionResponse.setHashId(performTransactionRequest.getHashId());

        return performTransactionResponse;
    }

    public WalletTransactionHistoryResponse getWalletTransactionHistory(Long walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            throw new WalletNotFoundException(walletId);
        }

        return populateWalletTransactionHistoryResponse(wallet.get());
    }

    private WalletTransactionHistoryResponse populateWalletTransactionHistoryResponse(Wallet wallet) {
        WalletTransactionHistoryResponse walletTransactionHistoryResponse = new WalletTransactionHistoryResponse();
        walletTransactionHistoryResponse.setId(wallet.getId());
        walletTransactionHistoryResponse.setName(wallet.getName());
        walletTransactionHistoryResponse.setOwner(wallet.getPlayer().getName());
        walletTransactionHistoryResponse.setBalance(wallet.getBalance());
        walletTransactionHistoryResponse.setCurrency(wallet.getCurrency());
        walletTransactionHistoryResponse.setCreatedDate(wallet.getCreatedDate());
        walletTransactionHistoryResponse.setTransactionResponseList(new ArrayList<>());

        wallet.getTransactionList().stream()
                .map(MapperUtils::mapToTransactionResponse).
                forEach(transactionResponse -> walletTransactionHistoryResponse
                        .getTransactionResponseList().add(transactionResponse));

        return walletTransactionHistoryResponse;
    }
}
