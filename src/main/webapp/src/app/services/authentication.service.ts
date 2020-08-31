import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable()
export class AuthenticationService {

  private authenticated:boolean = false;

  constructor(private http: HttpClient, private router:Router) {
  }

  authenticate(username : string, password : string):void {

    const headers = new HttpHeaders(
      {authorization : 'Basic ' + btoa(username + ':' + password)}
    );

    this.http.get('user', {headers: headers})
      .subscribe(response => {
        this.authenticated = true;
        this.router.navigateByUrl('/game');
      }, () => {
        this.authenticated = false;
      });
  }

  logout() {
    return this.http.post('logout', {}).subscribe(() => {
      this.authenticated = false;
      this.router.navigateByUrl('/login');
    });
  }

  isAuthenticated():boolean {
    return this.authenticated;
  }
}
