package com.leovegas.walletservice.exception;

import com.leovegas.walletservice.constant.FieldKeys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<?> handlePlayerNotFoundException(PlayerNotFoundException playerNotFoundException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.PLAYER_NOT_FOUND);
        errorDetails.setValue(playerNotFoundException.getPlayerName());
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<?> handleNullFieldException(NullFieldException nullFieldException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.FIELD_CANT_BE_NULL);
        errorDetails.setField(nullFieldException.getFieldName());

        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<?> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException playerAlreadyExistsException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.PLAYER_ALREADY_EXISTS);
        errorDetails.setValue(playerAlreadyExistsException.getPlayerName());
        errorDetails.setField(FieldKeys.PLAYER_NAME);

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPlayerNameException.class)
    public ResponseEntity<?> handleNullPlayerNameException(NullPlayerNameException nullPlayerNameException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.PLAYER_NAME_CANT_BE_NULL);
        errorDetails.setValue(nullPlayerNameException.getPlayerName());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<?> handleInvalidCurrencyException(InvalidCurrencyException invalidCurrencyException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.INVALID_CURRENCY);
        errorDetails.setValue(invalidCurrencyException.getInvalidCurrency());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<?> handleNegativeBalanceException(NegativeBalanceException negativeBalanceException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.NEGATIVE_BALANCE_NOT_ACCEPTED);
        errorDetails.setValue(negativeBalanceException.getBalance().toString());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NonUniqueTransactionHashIdException.class)
    public ResponseEntity<?> handleNonUniqueTransactionHashIdException(NonUniqueTransactionHashIdException nonUniqueTransactionHashIdException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.NON_UNIQUE_TRANSACTION_HASH_ID);
        errorDetails.setValue(nonUniqueTransactionHashIdException.getTransactionHashId());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CurrencyMisMatchException.class)
    public ResponseEntity<?> handleCurrencyMisMatchException(CurrencyMisMatchException currencyMisMatchException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.CURRENCY_MISMATCH);
        errorDetails.setValue(currencyMisMatchException.getMismatchCurrency());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<?> handleNotEnoughBalanceException(NotEnoughBalanceException notEnoughBalanceException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.NOT_ENOUGH_BALANCE);
        errorDetails.setDetails("Wallet balance : " + notEnoughBalanceException.getWalletBalance());

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<?> handleWalletNotFoundException(WalletNotFoundException walletNotFoundException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setErrorCode(ErrorMessageKeys.WALLET_NOT_FOUND);
        errorDetails.setValue(walletNotFoundException.getWalletId().toString());
        errorDetails.setField(FieldKeys.WALLET_ID);

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
