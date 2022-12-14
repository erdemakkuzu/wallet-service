package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.exception.PlayerAlreadyExistsException;
import com.leovegas.walletservice.exception.PlayerNotFoundException;
import com.leovegas.walletservice.model.CreatePlayerRequest;
import com.leovegas.walletservice.model.CreatePlayerResponse;
import com.leovegas.walletservice.model.GetPlayerResponse;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.service.PlayerService;
import com.leovegas.walletservice.util.MapperUtils;
import com.leovegas.walletservice.util.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    @Autowired
    PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public GetPlayerResponse getPlayer(String name) {
        PlayerUtils.validatePlayerNameLength(name);
        Player player = playerRepository.findByName(name);

        if (player == null) {
            logger.error("Player not found. Player name:" + name);
            throw new PlayerNotFoundException(name);
        }

        return MapperUtils.mapToGetPlayerResponse(player);
    }

    @Transactional
    public CreatePlayerResponse createPlayer(CreatePlayerRequest createPlayerRequest) {
        PlayerUtils.validateCreatePlayerRequest(createPlayerRequest);

        Player existedPlayer = playerRepository.findByName(createPlayerRequest.getName());
        if (existedPlayer != null) {
            logger.error("Player already exists: " + createPlayerRequest.getName());
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

    public List<GetPlayerResponse> getPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream()
                .map(MapperUtils::mapToGetPlayerResponse)
                .collect(Collectors.toList());

    }
}
