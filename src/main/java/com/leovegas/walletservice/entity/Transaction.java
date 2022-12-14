package com.leovegas.walletservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;
}
