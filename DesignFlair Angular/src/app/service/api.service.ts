import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private BASE_URL = "http://localhost:"
  private port = "8080"
  constructor(private http: HttpClient) { }

  public login(username: string, password: string) {
    const user  ={
      username: username,
      password: password
    }
    const url = this.BASE_URL + this.port + "/user/generateToken"
    return this.http.post<any>(url, user)
  }

  public register(user: any) {
    const url = this.BASE_URL + this.port + "/user"
    return this.http.post<any>(url, user)
  }

  public generateImage(prompt: string) {
    const inputs = {
      inputs: prompt
    }
    const headers = new HttpHeaders({
      'Authorization': 'Bearer '+localStorage['token'],
      'Content-Type': 'application/json'
    });
    const url = this.BASE_URL + this.port + "/design/v1/get"
    return this.http.post<any>(url, inputs, {headers})
  }

  public upscaleImage(image: any) {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer '+localStorage['token'],
      'Content-Type': 'application/json'
    });
    const url = this.BASE_URL + this.port + "/design/v1/upscale"
    return this.http.post<any>(url, image, {headers})
  }


}
