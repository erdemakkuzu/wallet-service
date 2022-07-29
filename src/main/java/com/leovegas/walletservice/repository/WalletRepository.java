package com.leovegas.walletservice.repository;

import com.leovegas.walletservice.entity.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
}
