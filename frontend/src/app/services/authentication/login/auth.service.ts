import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(data: {email: string, password: string}) {
    return this.http.post("http://localhost:9900/users/login", data);
  }
}
