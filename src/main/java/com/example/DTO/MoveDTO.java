package com.example.DTO;

import com.example.enums.Piece;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveDTO extends ComputerMoveDTO{

    private Date created;
    private String userName;
    private Piece playerPieceCode;
}
