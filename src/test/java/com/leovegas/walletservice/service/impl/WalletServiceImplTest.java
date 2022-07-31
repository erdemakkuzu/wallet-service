package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Transaction;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.exception.*;
import com.leovegas.walletservice.model.*;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.TransactionRepository;
import com.leovegas.walletservice.repository.WalletRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WalletServiceImplTest {

    private static final Long DUMMY_PLAYER_ID = 113L;
    private static final String DUMMY_PLAYER_NAME = "dummyPlayerName";
    private static final String DUMMY_PLAYER_FIRST_NAME = "first";
    private static final String DUMMY_PLAYER_LAST_NAME = "last";
    private static final String DUMMY_PLAYER_GENDER = "female";
    private static final int DUMMY_PLAYER_AGE = 28;
    private static final Date DUMMY_PLAYER_CREATED_DATE = new Date();

    private static final String DUMMY_WALLET_NAME = "dummy wallet name";
    private static final Double DUMMY_INITIAL_WALLET_BALANCE = 55.3;
    private static final Double DUMMY_INITIAL_WALLET_INVALID_BALANCE = -55.3;
    private static final String VALID_CURRENCY = "EU";
    private static final String DIFFERENT_CURRENCY = "USD";
    private static final String INVALID_CURRENCY = "BTC";
    private static final Long DUMMY_WALLET_ID = 2L;
    private static final Date DUMMY_WALLET_CREATED_DATE = new Date();

    private static final String DUMMY_TRANSACTION_HASH_ID = "1234asd";
    private static final Double DUMMY_TRANSACTION_AMOUNT = 55.3;
    private static final Double DUMMY_HIGH_TRANSACTION_AMOUNT = 588.3;
    private static final Double DUMMY_NEGATIVE_TRANSACTION_AMOUNT = -8.3;
    private static final String DUMMY_TRANSACTION_NOTE = "this is a note";
    private static final Long DUMMY_TRANSACTION_ID = 1L;
    private static final int DUMMY_WALLET_TRANSACTION_COUNT = 15;

    Player player = generateDummyPlayer();

    private WalletRepository walletRepository;
    private PlayerRepository playerRepository;
    private TransactionRepository transactionRepository;
    WalletServiceImpl walletServiceImpl;

    @Before
    public void setup() {
        playerRepository = mock(PlayerRepository.class);
        walletRepository = mock(WalletRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        walletServiceImpl = new WalletServiceImpl(walletRepository, playerRepository, transactionRepository);
    }

    @Test
    public void testCreateWallet_happyFlow() {
        CreateWalletRequest createWalletRequest =
                new CreateWalletRequest(DUMMY_WALLET_NAME, DUMMY_INITIAL_WALLET_BALANCE, VALID_CURRENCY);


        Wallet wallet = generateDummyWallet();

        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(player);
        when(walletRepository.save(any())).thenReturn(wallet);

        CreateWalletResponse createWalletResponse =
                walletServiceImpl.createWallet(DUMMY_PLAYER_NAME, createWalletRequest);

        assertEquals(DUMMY_WALLET_ID, createWalletResponse.getId());
        assertEquals(DUMMY_WALLET_CREATED_DATE, createWalletResponse.getCreatedDate());
        assertEquals(player.getName(), createWalletResponse.getOwner());
        assertEquals(DUMMY_INITIAL_WALLET_BALANCE, createWalletResponse.getBalance());
        assertEquals(VALID_CURRENCY, createWalletResponse.getCurrency());
    }

    @Test(expected = NegativeBalanceException.class)
    public void testCreateWallet_InvalidBalanceException() {
        CreateWalletRequest createWalletRequest =
                new CreateWalletRequest(DUMMY_WALLET_NAME, DUMMY_INITIAL_WALLET_INVALID_BALANCE, VALID_CURRENCY);
        walletServiceImpl.createWallet(DUMMY_PLAYER_NAME, createWalletRequest);
    }

    @Test(expected = NullFieldException.class)
    public void testCreateWallet_NullWalletName_Exception() {
        CreateWalletRequest createWalletRequest =
                new CreateWalletRequest(null, DUMMY_INITIAL_WALLET_BALANCE, VALID_CURRENCY);
        walletServiceImpl.createWallet(DUMMY_PLAYER_NAME, createWalletRequest);
    }

    @Test(expected = NullFieldException.class)
    public void testCreateWallet_BlankWalletName_Exception() {
        CreateWalletRequest createWalletRequest =
                new CreateWalletRequest("", DUMMY_INITIAL_WALLET_BALANCE, VALID_CURRENCY);
        walletServiceImpl.createWallet(DUMMY_PLAYER_NAME, createWalletRequest);
    }

    @Test(expected = PlayerNotFoundException.class)
    public void testCreateWallet_playerNotFound_Exception() {
        CreateWalletRequest createWalletRequest =
                new CreateWalletRequest(DUMMY_WALLET_NAME, DUMMY_INITIAL_WALLET_BALANCE, VALID_CURRENCY);
        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(null);
        walletServiceImpl.createWallet(DUMMY_PLAYER_NAME, createWalletRequest);
    }

    @Test
    public void performCredit_happyFlow() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(null);

        PerformTransactionResponse performTransactionResponse =
                walletServiceImpl.performCredit(DUMMY_WALLET_ID, performTransactionRequest);

        assertEquals(VALID_CURRENCY, performTransactionResponse.getCurrency());
        assertEquals(DUMMY_TRANSACTION_HASH_ID, performTransactionResponse.getTransactionHashId());
        assertEquals(DUMMY_WALLET_ID, performTransactionResponse.getWalletId());
        assertEquals(wallet.getBalance(), performTransactionResponse.getCurrentBalance());

    }

    @Test
    public void performDebit() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(null);

        PerformTransactionResponse performTransactionResponse =
                walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);

        assertEquals(VALID_CURRENCY, performTransactionResponse.getCurrency());
        assertEquals(DUMMY_TRANSACTION_HASH_ID, performTransactionResponse.getTransactionHashId());
        assertEquals(DUMMY_WALLET_ID, performTransactionResponse.getWalletId());
        assertEquals(wallet.getBalance(), performTransactionResponse.getCurrentBalance());

    }

    @Test(expected = CurrencyMisMatchException.class)
    public void performDebit_currencyMismatch_exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        DIFFERENT_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(null);

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = NonUniqueTransactionHashIdException.class)
    public void performDebit_nonUniqueHashId_Exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(new Transaction());

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = WalletNotFoundException.class)
    public void performDebit_walletNotFound_Exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.empty());

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = NullFieldException.class)
    public void performDebit_nullHashId_Exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(null, DUMMY_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = InvalidCurrencyException.class)
    public void performDebit_invalidCurrency_Exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_TRANSACTION_AMOUNT,
                        INVALID_CURRENCY, DUMMY_TRANSACTION_NOTE);

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = NegativeBalanceException.class)
    public void performDebit_invalidAmountException() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_NEGATIVE_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(null);

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void performDebit_notEnoughBalance_Exception() {
        PerformTransactionRequest performTransactionRequest =
                new PerformTransactionRequest(DUMMY_TRANSACTION_HASH_ID, DUMMY_HIGH_TRANSACTION_AMOUNT,
                        VALID_CURRENCY, DUMMY_TRANSACTION_NOTE);
        Wallet wallet = generateDummyWallet();
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));
        when(transactionRepository.findByHashId(eq(DUMMY_TRANSACTION_HASH_ID))).thenReturn(null);

        walletServiceImpl.performDebit(DUMMY_WALLET_ID, performTransactionRequest);
    }

    @Test
    public void getWalletTransactionHistory_happyFlow() {
        Wallet wallet = generateDummyWallet();
        List<Transaction> transactionList = new ArrayList<>();
        wallet.setTransactionList(transactionList);

        for (int i = 0; i < DUMMY_WALLET_TRANSACTION_COUNT; i++) {
            transactionList.add(new Transaction(DUMMY_TRANSACTION_ID,
                    DUMMY_TRANSACTION_HASH_ID,
                    DUMMY_TRANSACTION_AMOUNT,
                    TransactionType.DEBIT.getTransactionType(), DUMMY_TRANSACTION_NOTE, new Date(), VALID_CURRENCY, wallet));
        }
        when(walletRepository.findById(eq(DUMMY_WALLET_ID))).thenReturn(Optional.of(wallet));

        assertEquals(DUMMY_WALLET_TRANSACTION_COUNT, wallet.getTransactionList().size());
    }

    public Wallet generateDummyWallet() {
        return new Wallet(DUMMY_WALLET_ID,
                DUMMY_WALLET_NAME,
                DUMMY_INITIAL_WALLET_BALANCE,
                DUMMY_WALLET_CREATED_DATE,
                VALID_CURRENCY, player, null);
    }

    public static Player generateDummyPlayer() {
        return new Player(DUMMY_PLAYER_ID,
                DUMMY_PLAYER_NAME,
                DUMMY_PLAYER_FIRST_NAME,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER,
                new ArrayList<>(),
                DUMMY_PLAYER_CREATED_DATE);
    }

}