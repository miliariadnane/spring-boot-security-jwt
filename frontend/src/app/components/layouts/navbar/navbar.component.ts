import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'src/app/services/authentication/account.service';
import { TokenService } from 'src/app/services/authentication/token.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  currentUser: null;

  constructor(
    private accountService: AccountService,
    private tokenService: TokenService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.accountService.authStatus.subscribe(res =>{
      this.currentUser = this.tokenService.getInfos();
    })
  }

  logout(){
    this.tokenService.remove();
    this.accountService.changeStatus(false);
    this.router.navigateByUrl("/login")
  }

}
