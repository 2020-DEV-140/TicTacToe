import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class GameService {

  constructor(private http:HttpClient) {
  }

  create(piece:string):Observable<any> {
    const gameDto = {
      piece: piece
    };

    return this.http.post<any>("/game/create", gameDto, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8'
      }
    });
  }

  move(boardRow:number, boardColumn:number):Observable<any> {
    const moveDto = {'boardRow': boardRow, 'boardColumn': boardColumn}

    return this.http.post<any>("/game/move", moveDto, {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8'
      }
    });
  }

  movesHistory():Observable<any> {
    return this.http.get('/game/list', {
      headers: {
        'Content-Type': 'application/json; charset=UTF-8'
      }
    })
  }
}
