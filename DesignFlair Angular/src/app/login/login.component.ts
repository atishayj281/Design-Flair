import { Component } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: any;
  password: any;
  constructor(private apiService: ApiService, private router: Router) {}
  login() {
    this.apiService.login(this.email, this.password).subscribe({
      next: (v) => this.handleSuccess(v),
      error: (e) => console.error(e),
      complete: () => console.info('complete') 
    })
  }
  handleSuccess(v: any): void {
    localStorage['token'] = v.token
    this.router.navigateByUrl('/design')
  }



}
