package com.leovegas.walletservice.service;

import com.leovegas.walletservice.model.CreatePlayerRequest;
import com.leovegas.walletservice.model.CreatePlayerResponse;
import com.leovegas.walletservice.model.GetPlayerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {

    GetPlayerResponse getPlayer(String playerName);

    List<GetPlayerResponse> getPlayers();

    CreatePlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest);

}
