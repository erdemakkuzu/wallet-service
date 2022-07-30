package com.leovegas.walletservice.service;

import com.leovegas.walletservice.model.*;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

    CreateWalletResponse createWallet(String playerName, CreateWalletRequest createWalletRequest);

    PerformTransactionResponse performCredit(Long walletId, PerformTransactionRequest performTransactionRequest);

    PerformTransactionResponse performDebit(Long walletId, PerformTransactionRequest performTransactionRequest);

    WalletTransactionHistoryResponse getWalletTransactionHistory(Long walletId);

}
