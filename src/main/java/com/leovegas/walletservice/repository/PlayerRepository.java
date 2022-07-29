package com.leovegas.walletservice.repository;

import com.leovegas.walletservice.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    Player findByName(String playerName);

}
