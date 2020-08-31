package com.example.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.ComputerMoveDTO;
import com.example.DTO.GameDTO;
import com.example.DTO.HumanMoveDTO;
import com.example.DTO.MoveDTO;
import com.example.domain.Game;
import com.example.domain.Move;
import com.example.domain.Player;
import com.example.enums.GameStatus;
import com.example.enums.Piece;
import com.example.service.GameService;
import com.example.service.MoveService;
import com.example.service.PlayerService;


@RestController
@RequestMapping("/game")
public class GameController {

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping(value = "/create")
    public ComputerMoveDTO createNewGame(@RequestBody GameDTO gameDTO) {
        Player humanPlayer = playerService.getLoggedUser();
        Game game = gameService.createNewGame(humanPlayer, gameDTO);
        httpSession.setAttribute("gameId", game.getId());

        ComputerMoveDTO computerMove = new ComputerMoveDTO(GameStatus.IN_PROGRESS);
        if(gameDTO.getPiece().equals(Piece.O)) {
            Move autoMove = moveService.autoCreateMove(gameService.getGame(game.getId()));
            computerMove.setBoardRow(autoMove.getBoardRow());
            computerMove.setBoardColumn(autoMove.getBoardColumn());
        }

        return computerMove;
    }

    @PostMapping(value = "/move")
    public ComputerMoveDTO humanMove(@RequestBody HumanMoveDTO createMoveDTO) {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        logger.info("move to insert:" + createMoveDTO.getBoardColumn() + createMoveDTO.getBoardRow());

        moveService.createMove(gameService.getGame(gameId), playerService.getLoggedUser(), createMoveDTO);
        Game game = gameService.getGame(gameId);

        GameStatus currentStatus = moveService.checkCurrentGameStatus(game);
        gameService.updateGameStatus(gameService.getGame(gameId), currentStatus);
        ComputerMoveDTO computerMove = new ComputerMoveDTO(currentStatus);
        if(currentStatus.equals(GameStatus.IN_PROGRESS)) {
            Move autoMove = moveService.autoCreateMove(gameService.getGame(gameId));
            currentStatus = moveService.checkCurrentGameStatus(game);
            gameService.updateGameStatus(gameService.getGame(gameId), currentStatus);
            computerMove = new ComputerMoveDTO(autoMove.getBoardRow(), autoMove.getBoardColumn(), currentStatus);
        }

        return computerMove;
    }

    @GetMapping(value = "/list")
    public List<MoveDTO> getMovesInGame() {
        Long gameId = (Long) httpSession.getAttribute("gameId");
        return moveService.getMovesInGame(gameService.getGame(gameId));
    }

    @GetMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
