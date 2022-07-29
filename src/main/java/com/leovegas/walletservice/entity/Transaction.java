package com.leovegas.walletservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash", unique = true)
    private String hash;

    @Column(name="ammount")
    private Double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private Date date;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;

    @Column(name = "fail_reason")
    private String failReason;

    @ManyToOne
    private Wallet wallet;
}
