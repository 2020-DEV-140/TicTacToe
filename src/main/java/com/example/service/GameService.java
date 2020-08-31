package com.example.service;

import com.example.DTO.GameDTO;
import com.example.domain.Game;
import com.example.domain.Player;
import com.example.enums.GameStatus;
import com.example.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GameService {

	@Autowired
    private GameRepository gameRepository;

    @Transactional
    public Game createNewGame(Player player, GameDTO gameDTO) {
        Game game = new Game();
        game.setHumanPlayer(player);
        game.setHumanPlayerPieceCode(gameDTO.getPiece());
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game.setCreated(new Date());
        return gameRepository.save(game);
    }

    @Transactional
    public Game updateGameStatus(Game game, GameStatus gameStatus) {
        Game g = getGame(game.getId());
        g.setGameStatus(gameStatus);
        return gameRepository.save(game);
    }

    public List<Game> getPlayerGames(Player player) {
        return gameRepository.findByGameStatus(GameStatus.IN_PROGRESS).stream().filter(game -> game.getHumanPlayer() == player).collect(Collectors.toList());
    }

    public Game getGame(Long id) {
        return gameRepository.findById(id).get();
    }
}
