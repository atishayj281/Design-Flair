import { Component } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {

  constructor(private apiService: ApiService, private router: Router) {}

  confirmPassword: any;
  password: any;
  email: any;
  lastName: any;
  firstName: any;

  validate(){
    if(this.email != '' &&
    this.lastName != '' &&
    this.firstName != '' &&
    this.password != '' &&
    this.password == this.confirmPassword) return true;
    return false;
  }

  register() {
    if(this.validate()) {
      let user = {
        firstName: this.firstName,
        lastName: this.lastName,
        username: this.email,
        password: this.password
      }
      console.log(user);
      
      this.apiService.register(user).subscribe({
        next: (v) => this.handleSuccess(v),
        error: (e) => console.error(e),
        complete: () => console.info('complete') 
      })
    }
  }

  clearForm() {
    throw new Error('Method not implemented.');
  }

  handleSuccess(result: any) {
    console.log(result);
    localStorage['token'] = result.token;
    let message = result.message    
    this.router.navigateByUrl('/design')
  }



}

