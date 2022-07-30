package com.leovegas.walletservice.util;

import com.leovegas.walletservice.constant.FieldKeys;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.exception.NullPlayerNameException;
import com.leovegas.walletservice.model.CreatePlayerRequest;

public class PlayerUtils {

    public static void validateCreatePlayerRequest(CreatePlayerRequest createPlayerRequest) {
        if (createPlayerRequest.getName() == null) {
            throw new NullFieldException(FieldKeys.PLAYER_NAME);
        }

        if (createPlayerRequest.getFirstName() == null) {
            throw new NullFieldException(FieldKeys.PLAYER_FIRST_NAME);
        }

        if (createPlayerRequest.getLastName() == null) {
            throw new NullFieldException(FieldKeys.PLAYER_LAST_NAME);
        }

        validatePlayerPathVariable(createPlayerRequest.getName());

    }

    public static void validatePlayerPathVariable(String playerName) {
        if (playerName.isEmpty() || playerName.isBlank()) {
            throw new NullPlayerNameException(playerName);
        }
    }

}
