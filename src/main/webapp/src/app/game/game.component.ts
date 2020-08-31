import { Component, OnInit } from '@angular/core';
import { GameService } from './../services/game.service';
import { AuthenticationService } from './../services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

export interface Column {
  id: string;
  letter: string;
  class: string
}

export enum PlayerType {
  COMPUTER = 'COMPUTER',
  HUMAN = 'HUMAN'
}

export enum GameStatus {
  IN_PROGRESS = 'IN_PROGRESS',
  HUMAN_WON = 'HUMAN_WON',
  COMPUTER_WON = 'COMPUTER_WON',
  TIE = 'TIE'
}

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  gamePanel:Column[][];

  selectedOption:string;
  isCreateNewAllowed:boolean = true;
  gameHistoryList;

  constructor(private gameService:GameService) {
  }

  ngOnInit():void {
    this.reset();
  }

  reset() {
    this.gameHistoryList = [];
    this.gamePanel = [
      [
        {'id': '11', 'letter': '', 'class': 'box'},
        {'id': '12', 'letter': '', 'class': 'box'},
        {'id': '13', 'letter': '', 'class': 'box'}
      ],
      [
        {'id': '21', 'letter': '', 'class': 'box'},
        {'id': '22', 'letter': '', 'class': 'box'},
        {'id': '23', 'letter': '', 'class': 'box'}
      ],
      [
        {'id': '31', 'letter': '', 'class': 'box'},
        {'id': '32', 'letter': '', 'class': 'box'},
        {'id': '33', 'letter': '', 'class': 'box'}
      ]
    ];
  }

  createGame() {
    this.reset();

    this.gameService.create(this.selectedOption).subscribe((result) => {
      // This code will be executed when the HTTP call returns successfully
      if (result.boardRow && result.boardColumn) {
        this.markMove(result.boardRow, result.boardColumn, PlayerType.COMPUTER,
          this.gameOptions.filter(option => option.name !== this.selectedOption).map(opt => opt.name)[0]);
      }

      this.isCreateNewAllowed = false;
    }, error => {
      console.error(`There was an error creating game`);
    });

  }

  //refresh() {
  //  this.gameService.movesHistory().subscribe((result) => {
  //    this.gameHistoryList = result;
  //
  //    this.gameHistoryList.forEach(moveList => {
  //      this.markMove(moveList.boardRow, moveList.boardColumn, moveList.playerPieceCode);
  //    })
  //  }, error => {
  //    console.error(`There was an error refreshing game history`);
  //  });
  //}

  private markMove(boardRow:number, boardColumn:number, player:PlayerType, piece) {
    this.gamePanel.flat().forEach(t => {
      if (t.id == (boardRow + '' + boardColumn)) {
        t.letter = piece;
      }
    })

    this.gameHistoryList.push({boardRow: boardRow, boardColumn: boardColumn, player: player, piece: piece});
  }

  markPlayerMove(column) {
    const boardRow:number = +(column.id.charAt(0));
    const boardColumn:number = +(column.id.charAt(1));

    if (this.gamePanel.flat().filter(m => m.id == column.id && column.letter).length > 0) {
      console.log('position already taken by piece');
      return;
    }

    this.gameService.move(boardRow, boardColumn).subscribe((result) => {
      // player move
      this.markMove(boardRow, boardColumn, PlayerType.HUMAN, this.selectedOption);

      // computer move
      if (result.boardRow > 0 && result.boardColumn > 0) {
        this.markMove(result.boardRow, result.boardColumn, PlayerType.COMPUTER,
          this.gameOptions.filter(option => option.name !== this.selectedOption).map(opt => opt.name)[0]);
      }

      if (result.gameStatus !== GameStatus.IN_PROGRESS) {
        setTimeout("alert('" + result.gameStatus + "')", 20);
        this.isCreateNewAllowed = true;
      }

    }, error => {
      console.error(`There was an error on player move`);
    });
  }




  get gameOptions() {
    return [
      {name: 'X'},
      {name: 'O'}
    ];
  }
}
