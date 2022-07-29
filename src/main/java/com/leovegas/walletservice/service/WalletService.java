package com.leovegas.walletservice.service;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.exception.PlayerNotFoundException;
import com.leovegas.walletservice.model.CreateWalletRequest;
import com.leovegas.walletservice.model.CreateWalletResponse;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.WalletRepository;
import com.leovegas.walletservice.util.MapperUtils;
import com.leovegas.walletservice.util.PlayerUtils;
import com.leovegas.walletservice.util.WalletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class WalletService {

    WalletRepository walletRepository;
    PlayerRepository playerRepository;

    @Autowired
    WalletService(WalletRepository walletRepository,
                  PlayerRepository playerRepository){
        this.walletRepository = walletRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public CreateWalletResponse createWallet(String playerName,
                                             CreateWalletRequest createWalletRequest) {
        PlayerUtils.validatePlayerPathVariable(playerName);
        WalletUtils.validateCreateWalletRequest(createWalletRequest);

        Player player = playerRepository.findByName(playerName);

        if(player == null){
            throw new PlayerNotFoundException(playerName);
        }

        Wallet wallet = saveWallet(createWalletRequest,player);

        return MapperUtils.mapToCreateWalletResponse(wallet, player.getName());
    }

    private Wallet saveWallet(CreateWalletRequest createWalletRequest,
                              Player player){
        Wallet wallet = new Wallet();
        wallet.setName(createWalletRequest.getName());
        wallet.setBalance(createWalletRequest.getBalance() != null ? createWalletRequest.getBalance() : 0.0);
        wallet.setCurrency(createWalletRequest.getCurrency());
        wallet.setCreatedDate(new Date());
        wallet.setPlayer(player);

        return walletRepository.save(wallet);
    }

}
