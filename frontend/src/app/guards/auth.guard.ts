import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AccountService } from '../services/authentication/account.service';
import { TokenService } from '../services/authentication/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  /* this guards test if isn't auth so redirect to login */

  currentUser: any;

  constructor(
    private tokenService: TokenService,
    private accountService: AccountService,
    private router: Router
  ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {

      if(!this.tokenService.loggedIn()) {
        this.tokenService.remove();
        this.accountService.changeStatus(false);
        this.router.navigateByUrl("/login");
        return false;
      }
      return true;
  }
}
