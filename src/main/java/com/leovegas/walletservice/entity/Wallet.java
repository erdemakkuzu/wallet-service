package com.leovegas.walletservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "curency")
    private String currency;

    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactionList;

}
