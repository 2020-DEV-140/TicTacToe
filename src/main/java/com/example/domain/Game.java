package com.example.domain;

import com.example.enums.GameStatus;
import com.example.enums.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player humanPlayer;

    @Enumerated(EnumType.STRING)
    private Piece humanPlayerPieceCode;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @Column(name = "created", nullable = false)
    private Date created;

    public Game(Player humanPlayer, Piece humanPlayerPiece, GameStatus gameStatus) {
        super();
        this.humanPlayer = humanPlayer;
        this.humanPlayerPieceCode = humanPlayerPiece;
        this.gameStatus = gameStatus;
    }

}
