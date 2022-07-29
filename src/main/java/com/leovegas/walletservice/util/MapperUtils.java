package com.leovegas.walletservice.util;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.entity.Wallet;
import com.leovegas.walletservice.model.CreatePlayerResponse;
import com.leovegas.walletservice.model.CreateWalletResponse;
import com.leovegas.walletservice.model.GetPlayerResponse;
import com.leovegas.walletservice.model.GetWalletResponse;

import java.util.ArrayList;
import java.util.List;

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
        GetPlayerResponse getPlayerResponse = new GetPlayerResponse();
        getPlayerResponse.setId(player.getId());
        getPlayerResponse.setName(player.getName());
        getPlayerResponse.setFirstName(player.getFirstName());
        getPlayerResponse.setLastName(player.getLastName());

        List<GetWalletResponse> walletResponseList = new ArrayList();

        player.getWalletList().forEach(wallet -> {
            GetWalletResponse walletResponse = new GetWalletResponse();
            walletResponse.setId(wallet.getId());
            walletResponse.setName(wallet.getName());
            walletResponse.setBalance(wallet.getBalance());
            walletResponse.setCurrency(wallet.getCurrency());
            walletResponse.setCreateDate(wallet.getCreatedDate());
            walletResponseList.add(walletResponse);
        });

        getPlayerResponse.setWalletList(walletResponseList);

        return getPlayerResponse;

    }
}
