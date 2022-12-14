package com.leovegas.walletservice.controller;

import com.leovegas.walletservice.constant.Operations;
import com.leovegas.walletservice.model.*;
import com.leovegas.walletservice.service.WalletService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping(value = "/{playerName}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = Operations.CREATE_WALLET)
    public ResponseEntity<CreateWalletResponse> createWallet(@PathVariable("playerName") final String playerName,
                                                             @RequestBody CreateWalletRequest createWalletRequest) {
        return new ResponseEntity<>(walletService.createWallet(playerName, createWalletRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{walletId}/credit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = Operations.CREDIT)
    public ResponseEntity<PerformTransactionResponse> performCredit(@PathVariable("walletId") final Long walletId,
                                                                    @RequestBody PerformTransactionRequest performTransactionRequest) {
        return new ResponseEntity<>(walletService.performCredit(walletId, performTransactionRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{walletId}/debit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = Operations.DEBIT)
    public ResponseEntity<PerformTransactionResponse> performDebit(@PathVariable("walletId") final Long walletId,
                                                                   @RequestBody PerformTransactionRequest performTransactionRequest) {
        return new ResponseEntity<>(walletService.performDebit(walletId, performTransactionRequest), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{walletId}/transaction-history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = Operations.TRANSACTION_HISTORY)
    public ResponseEntity<WalletTransactionHistoryResponse> getWalletTransactionHistory(@PathVariable("walletId") final Long walletId) {
        return new ResponseEntity<>(walletService.getWalletTransactionHistory(walletId), HttpStatus.OK);
    }
}
