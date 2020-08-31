package com.example.repository;

import com.example.domain.Game;
import com.example.enums.GameStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    List<Game> findByGameStatus(GameStatus gameStatus);
}
