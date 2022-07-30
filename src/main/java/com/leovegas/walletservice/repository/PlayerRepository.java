package com.leovegas.walletservice.repository;

import com.leovegas.walletservice.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByName(String playerName);

}
