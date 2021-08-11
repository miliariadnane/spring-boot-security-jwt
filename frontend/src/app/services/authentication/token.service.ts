import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  /* set token in local storage */
  set(data: any) {
    localStorage.setItem('token', data.token);
    localStorage.setItem('id', data.id);
  }

  /* handle method to not show the set */
  handle(data: any) {
    this.set(data);
  }

  /* retreive token from local storage */
  getToken() {
    return localStorage.getItem('token');
  }

  /* retreive id from local storage */
  getId() {
    return localStorage.getItem('id');
  }

  remove() {
    localStorage.removeItem('token');
    localStorage.removeItem('id');
  }

  /* token do not crypted so we should decode the payload part (payload contain data) */
  /* atob : asci to binary is a method to decode, after we should converted to JSON */
  decode(payload: any) {
    
    return JSON.parse(atob(payload));
  }

  /* Retrieve payload part in token */
  payload(token: any) {
    const payload = token.split('.')[1];
    //console.log('payload : ', payload);
    return this.decode(payload);
  }

  /* test if is valid : test if token exist & compare payload */
  isValid() {
    const token = this.getToken();
    const id = this.getId();
      
    if (token) {

      const payload = this.payload(token);
      if (payload) {
        return id == payload.id;
      }
    }
    return false;
  }

  getInfos() {
    const token = this.getToken();

    if (token) {
      const payload = this.payload(token);
      return payload ? payload : null;
    }

    return null
  }

  loggedIn() {
    return this.isValid();
  }
}
