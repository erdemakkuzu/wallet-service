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

    @Column(name = "hash_id", unique = true)
    private String hashId;

    @Column(name = "ammount")
    private Double amount;

    @Column(name = "type")
    private String type;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private Date date;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    private Wallet wallet;
}
