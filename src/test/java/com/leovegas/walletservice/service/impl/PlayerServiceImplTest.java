package com.leovegas.walletservice.service.impl;

import com.leovegas.walletservice.entity.Player;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.exception.NullPlayerNameException;
import com.leovegas.walletservice.exception.PlayerAlreadyExistsException;
import com.leovegas.walletservice.exception.PlayerNotFoundException;
import com.leovegas.walletservice.model.CreatePlayerRequest;
import com.leovegas.walletservice.model.CreatePlayerResponse;
import com.leovegas.walletservice.model.GetPlayerResponse;
import com.leovegas.walletservice.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PlayerServiceImplTest {

    PlayerRepository playerRepository;
    PlayerServiceImpl playerServiceImpl;

    private static final Long DUMMY_PLAYER_ID = 113L;
    private static final String DUMMY_PLAYER_NAME = "dummyPlayerName";
    private static final String DUMMY_PLAYER_FIRST_NAME = "first";
    private static final String DUMMY_PLAYER_LAST_NAME = "last";
    private static final String DUMMY_PLAYER_GENDER = "female";
    private static final int DUMMY_PLAYER_AGE = 28;
    private static final Date DUMMY_PLAYER_CREATED_DATE = new Date();

    @Before
    public void setup() {
        playerRepository = mock(PlayerRepository.class);
        playerServiceImpl = new PlayerServiceImpl(playerRepository);
    }

    @Test
    public void getPlayerTest_happyFlow() {
        Player player = generateDummyPlayer();

        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(player);

        GetPlayerResponse getPlayerResponse = playerServiceImpl.getPlayer(DUMMY_PLAYER_NAME);

        assertEquals(DUMMY_PLAYER_ID, getPlayerResponse.getId());
        assertEquals(DUMMY_PLAYER_NAME, getPlayerResponse.getName());
        assertEquals(DUMMY_PLAYER_FIRST_NAME, getPlayerResponse.getFirstName());
        assertEquals(DUMMY_PLAYER_LAST_NAME, getPlayerResponse.getLastName());
        assertEquals(0, getPlayerResponse.getTotalBalanceList().size());
        assertEquals(0, getPlayerResponse.getWalletList().size());
    }

    @Test
    public void getPlayerSTest_happyFlow() {
        Player player = generateDummyPlayer();
        Player player2 = generateDummyPlayer();
        List<Player> playerList = List.of(player, player2);

        when(playerRepository.findAll()).thenReturn(playerList);
        assertEquals(2,  playerServiceImpl.getPlayers().size());

    }

    @Test(expected = PlayerNotFoundException.class)
    public void getPlayerTest_playerNotFound_Exception() {
        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(null);
        playerServiceImpl.getPlayer(DUMMY_PLAYER_NAME);
    }

    @Test
    public void createPlayerTest_happyFlow() {

        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(DUMMY_PLAYER_NAME,
                DUMMY_PLAYER_FIRST_NAME,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER);

        Player player = generateDummyPlayer();

        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(null);
        when(playerRepository.save(any())).thenReturn(player);

        CreatePlayerResponse getPlayerResponse = playerServiceImpl.createPlayer(createPlayerRequest);

        assertEquals(DUMMY_PLAYER_ID, getPlayerResponse.getPlayerId());
        assertEquals(DUMMY_PLAYER_NAME, getPlayerResponse.getPlayerName());
    }

    @Test(expected = NullFieldException.class)
    public void createPlayerTest_playerNameIsNull_NullFieldException() {

        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(DUMMY_PLAYER_NAME,
                null,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER);

        playerServiceImpl.createPlayer(createPlayerRequest);
    }

    @Test(expected = NullPlayerNameException.class)
    public void createPlayerTest_playerNameIsNull_PlayerNameIsBlank() {

        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest("",
                DUMMY_PLAYER_FIRST_NAME,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER);

        playerServiceImpl.createPlayer(createPlayerRequest);
    }

    @Test(expected = NullFieldException.class)
    public void createPlayerTest_playerFirstNameIsNull_NullFieldException() {

        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(null,
                DUMMY_PLAYER_FIRST_NAME,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER);

        playerServiceImpl.createPlayer(createPlayerRequest);
    }

    @Test(expected = PlayerAlreadyExistsException.class)
    public void createPlayerTest_playerAlreadyExists_Exception() {

        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest(DUMMY_PLAYER_NAME,
                DUMMY_PLAYER_FIRST_NAME,
                DUMMY_PLAYER_LAST_NAME,
                DUMMY_PLAYER_AGE,
                DUMMY_PLAYER_GENDER);

        Player player = generateDummyPlayer();

        when(playerRepository.findByName(eq(DUMMY_PLAYER_NAME))).thenReturn(player);
        playerServiceImpl.createPlayer(createPlayerRequest);
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