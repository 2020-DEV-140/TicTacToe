package com.example.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Game;
import com.example.domain.Move;
import com.example.domain.Player;
import com.example.enums.GameStatus;
import com.example.enums.Piece;
import com.example.repository.GameRepository;
import com.example.repository.MoveRepository;
import com.example.repository.PlayerRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryLayerTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MoveRepository moveRepository;

    private Player insertPlayer() {
        Player newPlayer = new Player("alex", "alex@alex.com", "alexpasswd");
        return playerRepository.save(newPlayer);
    }

    private Game insertGame() {
        Game game = new Game();
        game.setHumanPlayer(insertPlayer());
        game.setHumanPlayerPieceCode(Piece.X);
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game.setCreated(new Date());
        return gameRepository.save(game);
    }

    @Test
    public void whenFindByNameThenReturnPlayer() {
        Player newPlayer = insertPlayer();
        Player playerFound = playerRepository.findOneByUserName(newPlayer.getUserName());
        assertThat(playerFound.getUserName()).isEqualTo(newPlayer.getUserName());
    }

    @Test
    public void whenGameCreatedReturnGameList() {
        insertGame();
        assertThat(gameRepository.findAll()).isNotEmpty();
    }

    @Test
    public void shouldFindMovesForExistingame() {
        Game newGame = insertGame();

        Move move = new Move();
        move.setBoardColumn(1);
        move.setBoardRow(1);
        move.setCreated(new Date());
        move.setPlayer(null);
        move.setGame(newGame);
        moveRepository.save(move);

        assertThat(moveRepository.findByGame(newGame)).isNotEmpty();
    }

}
