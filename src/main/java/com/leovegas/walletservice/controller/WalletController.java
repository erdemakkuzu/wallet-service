package com.leovegas.walletservice.controller;

import com.leovegas.walletservice.model.CreateWalletRequest;
import com.leovegas.walletservice.model.CreateWalletResponse;
import com.leovegas.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private WalletService walletService;

    @Autowired
    WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping(value = "/{playerName}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateWalletResponse> createWallet(@PathVariable("playerName") final String playerName,
                                                             @RequestBody CreateWalletRequest createWalletRequest){
        return new ResponseEntity(walletService.createWallet(playerName, createWalletRequest), HttpStatus.CREATED);
    }
}
