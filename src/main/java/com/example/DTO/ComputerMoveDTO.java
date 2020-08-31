package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.enums.GameStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComputerMoveDTO extends HumanMoveDTO{

	protected GameStatus gameStatus;

	public ComputerMoveDTO(int boardRow, int boardColumn, GameStatus currentStatus) {
		super(boardRow, boardColumn);
		this.gameStatus = currentStatus;
	}
	
}
