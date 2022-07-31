package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Transaction;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.exception.*;
import com.leovegas.walletservice.model.*;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.TransactionRepository;
import com.leovegas.walletservice.repository.WalletRepository;
import com.leovegas.walletservice.service.WalletService;
import com.leovegas.walletservice.util.MapperUtils;
import com.leovegas.walletservice.util.PlayerUtils;
import com.leovegas.walletservice.util.WalletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;
    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    WalletServiceImpl(WalletRepository walletRepository,
                      PlayerRepository playerRepository,
                      TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public CreateWalletResponse createWallet(String playerName,
                                             CreateWalletRequest createWalletRequest) {
        PlayerUtils.validatePlayerNameLength(playerName);
        WalletUtils.validateCreateWalletRequest(createWalletRequest);

        Player player = playerRepository.findByName(playerName);

        if (player == null) {
            logger.error("Player not found. Player name:" + playerName);
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

        Transaction transaction = MapperUtils.mapToTransactionEntity(performTransactionRequest, wallet, TransactionType.CRE);

        wallet.setBalance(wallet.getBalance() + performTransactionRequest.getAmount());

        transactionRepository.save(transaction);

        return MapperUtils.toPerformTransactionResponse(wallet, performTransactionRequest);
    }

    @Transactional
    public PerformTransactionResponse performDebit(Long walletId,
                                                   PerformTransactionRequest performTransactionRequest) {
        Wallet wallet = validateTransactionRequestAndGetWallet(walletId, performTransactionRequest);

        Double walletBalance = wallet.getBalance();
        Double requestAmount = performTransactionRequest.getAmount();

        if (walletBalance < requestAmount) {
            logger.error("Wallet does not have enough balance: " + wallet.getBalance().toString());
            throw new NotEnoughBalanceException(wallet.getBalance().toString());
        }

        Transaction transaction = MapperUtils.mapToTransactionEntity(performTransactionRequest, wallet, TransactionType.DEBIT);

        wallet.setBalance(walletBalance - requestAmount);

        transactionRepository.save(transaction);

        return MapperUtils.toPerformTransactionResponse(wallet, performTransactionRequest);
    }

    private Wallet validateTransactionRequestAndGetWallet(Long walletId,
                                                          PerformTransactionRequest performTransactionRequest) {
        WalletUtils.validatePerformTransactionRequest(performTransactionRequest);

        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            logger.error("Wallet not found with given id : " + walletId);
            throw new WalletNotFoundException(walletId);
        }

        Transaction existedTransaction = transactionRepository.findByHashId(performTransactionRequest.getHashId());

        if (existedTransaction != null) {
            logger.error("Transaction id is not unique : " + performTransactionRequest.getHashId());
            throw new NonUniqueTransactionHashIdException(performTransactionRequest.getHashId());
        }

        if (!performTransactionRequest.getCurrency().equals(wallet.get().getCurrency())) {
            logger.error("Currency of the wallet doesn't match with transaction's currency : " + performTransactionRequest.getCurrency());
            throw new CurrencyMisMatchException(performTransactionRequest.getCurrency());
        }
        return wallet.get();
    }

    public WalletTransactionHistoryResponse getWalletTransactionHistory(Long walletId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);

        if (wallet.isEmpty()) {
            logger.error("Wallet not found with given id : " + walletId);
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
