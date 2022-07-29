package com.leovegas.walletservice.service;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.exception.PlayerAlreadyExistsException;
import com.leovegas.walletservice.exception.PlayerNotFoundException;
import com.leovegas.walletservice.model.CreatePlayerRequest;
import com.leovegas.walletservice.model.CreatePlayerResponse;
import com.leovegas.walletservice.model.GetPlayerResponse;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.WalletRepository;
import com.leovegas.walletservice.util.MapperUtils;
import com.leovegas.walletservice.util.PlayerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class PlayerService {

    PlayerRepository playerRepository;
    WalletRepository walletRepository;

    @Autowired
    PlayerService(PlayerRepository playerRepository,
                  WalletRepository walletRepository) {
        this.playerRepository = playerRepository;
        this.walletRepository = walletRepository;
    }

    public GetPlayerResponse getPlayer(String playerName){
        PlayerUtils.validatePlayerPathVariable(playerName);
        Player player = playerRepository.findByName(playerName);

        if(player == null){
            throw new PlayerNotFoundException(playerName);
        }

        return MapperUtils.mapToGetPlayerResponse(player);
    }

    @Transactional
    public CreatePlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest) {
        PlayerUtils.validateCreatePlayerRequest(createPlayerRequest);

        Player existedPlayer = playerRepository.findByName(createPlayerRequest.getName());
        if(existedPlayer != null){
            throw new PlayerAlreadyExistsException(createPlayerRequest.getName());
        }

        Player createdPlayer = savePlayer(createPlayerRequest);

        return new CreatePlayerResponse(createdPlayer.getName(), createdPlayer.getId());
    }

    private Player savePlayer(CreatePlayerRequest createPlayerRequest) {
        Player createdPlayer = new Player();
        createdPlayer.setName(createPlayerRequest.getName());
        createdPlayer.setFirstName(createPlayerRequest.getFirstName());
        createdPlayer.setLastName(createPlayerRequest.getLastName());
        createdPlayer.setAge(createPlayerRequest.getAge());
        createdPlayer.setGender(createPlayerRequest.getGender());
        createdPlayer.setCreatedDate(new Date());

        return playerRepository.save(createdPlayer);
    }
}
