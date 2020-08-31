import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './../services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm:FormGroup;
  submitted = false;
  credentialError = false;

  constructor(public authenticationService:AuthenticationService,
              private formBuilder:FormBuilder,
              private http:HttpClient,
              private router:Router) {
  }

  ngOnInit():void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  login() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.authenticationService.authenticate(this.loginForm.controls.username.value,
      this.loginForm.controls.password.value);

    setTimeout(() => {
      this.credentialError = !this.authenticationService.isAuthenticated();
    }, 1000);
  }

  logout() {
    this.authenticationService.logout();
  }
}
