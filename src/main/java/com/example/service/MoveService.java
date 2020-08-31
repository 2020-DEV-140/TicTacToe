package com.example.service;

import com.example.DTO.HumanMoveDTO;
import com.example.DTO.MoveDTO;
import com.example.domain.Game;
import com.example.domain.Move;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.enums.GameStatus;
import com.example.enums.Piece;
import com.example.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class MoveService {

	@Autowired
    private MoveRepository moveRepository;

	@Transactional
    public Move createMove(Game game, Player player, HumanMoveDTO createMoveDTO) {
        Move move = new Move();
        move.setBoardColumn(createMoveDTO.getBoardColumn());
        move.setBoardRow(createMoveDTO.getBoardRow());
        move.setCreated(new Date());
        move.setPlayer(player);
        move.setGame(game);

        return moveRepository.save(move);
    }

    @Transactional
    public Move autoCreateMove(Game game) {
        Move move = new Move();
        move.setBoardColumn(GameLogic.nextAutoMove(getTakenMovePositionsInGame(game)).getBoardColumn());
        move.setBoardRow(GameLogic.nextAutoMove(getTakenMovePositionsInGame(game)).getBoardRow());
        move.setCreated(new Date());
        move.setPlayer(null);
        move.setGame(game);

        return moveRepository.save(move);
    }

    public GameStatus checkCurrentGameStatus(Game game) {
        if (GameLogic.isWinner(getHumanMovePositionsInGame(game, game.getHumanPlayer()))) {
            return GameStatus.HUMAN_WON;
        } else if (GameLogic.isWinner(getComputerMovePositionsInGame(game))) {
            return GameStatus.COMPUTER_WON;
        } else if (GameLogic.isBoardIsFull(getTakenMovePositionsInGame(game))) {
            return GameStatus.TIE;
        } else {
            return GameStatus.IN_PROGRESS;
        }

    }

    public List<MoveDTO> getMovesInGame(Game game) {
        List<Move> movesInGame = moveRepository.findByGame(game);
        List<MoveDTO> moves = new ArrayList<>();
        Piece currentPiece = game.getHumanPlayerPieceCode();

        for(Move move :  movesInGame) {
            MoveDTO moveDTO = new MoveDTO();
            moveDTO.setBoardColumn(move.getBoardColumn());
            moveDTO.setBoardRow(move.getBoardRow());
            moveDTO.setCreated(move.getCreated());
            moveDTO.setGameStatus(move.getGame().getGameStatus());
            moveDTO.setUserName(move.getPlayer() == null ? new String("COMPUTER") : move.getPlayer().getUserName());
            moveDTO.setPlayerPieceCode(currentPiece);
            moves.add(moveDTO);

            currentPiece = currentPiece == Piece.X ? Piece.O : Piece.X;
        }

        return moves;
    }

    public List<Position> getTakenMovePositionsInGame(Game game) {
        return moveRepository.findByGame(game).stream().map(move -> new Position(move.getBoardRow(), move.getBoardColumn())).collect(Collectors.toList());
    }

    public List<Position> getHumanMovePositionsInGame(Game game, Player player) {
        return moveRepository.findByGameAndPlayer(game, player).stream().map(move -> new Position(move.getBoardRow(), move.getBoardColumn())).collect(Collectors.toList());
    }

    public List<Position> getComputerMovePositionsInGame(Game game) {
        return moveRepository.findByGameAndPlayerIsNull(game).stream().map(move -> new Position(move.getBoardRow(), move.getBoardColumn())).collect(Collectors.toList());
    }

    public int getTheNumberOfHumanPlayerMovesInGame(Game game, Player player) {
        return moveRepository.countByGameAndPlayer(game, player);
    }

    public int getTheNumberOfComputerMovesInGame(Game game) {
        return moveRepository.countByGameAndPlayerIsNull(game);
    }

    public boolean isPlayerTurn(Game game, Player humanPlayer) {
        return GameLogic.playerTurn(getTheNumberOfHumanPlayerMovesInGame(game, humanPlayer), getTheNumberOfComputerMovesInGame(game));
    }

}
