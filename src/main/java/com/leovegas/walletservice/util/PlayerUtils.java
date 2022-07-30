package com.leovegas.walletservice.util;

import com.leovegas.walletservice.constant.FieldKeys;
import com.leovegas.walletservice.exception.NullFieldException;
import com.leovegas.walletservice.exception.NullPlayerNameException;
import com.leovegas.walletservice.model.CreatePlayerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerUtils {

    private static final Logger logger = LoggerFactory.getLogger(PlayerUtils.class);

    public static void validateCreatePlayerRequest(CreatePlayerRequest createPlayerRequest) {
        if (createPlayerRequest.getName() == null) {
            logger.error("Field is null :" + FieldKeys.PLAYER_NAME);
            throw new NullFieldException(FieldKeys.PLAYER_NAME);
        }

        if (createPlayerRequest.getFirstName() == null) {
            logger.error("Field is null :" + FieldKeys.PLAYER_FIRST_NAME);
            throw new NullFieldException(FieldKeys.PLAYER_FIRST_NAME);
        }

        if (createPlayerRequest.getLastName() == null) {
            logger.error("Field is null :" + FieldKeys.PLAYER_LAST_NAME);
            throw new NullFieldException(FieldKeys.PLAYER_LAST_NAME);
        }

        validatePlayerNameLength(createPlayerRequest.getName());
    }

    public static void validatePlayerNameLength(String name) {
        if (name.isEmpty() || name.isBlank()) {
            logger.error("Empty string value:" + FieldKeys.PLAYER_NAME);
            throw new NullPlayerNameException(name);
        }
    }

}
