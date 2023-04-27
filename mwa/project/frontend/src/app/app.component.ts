import { Component, inject } from '@angular/core';
import { IState, initial_state } from './statesmodel';
import { Subscription } from 'rxjs';
import { StateService } from './state.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  state!: IState;
  subscription!: Subscription;
  private stateService = inject(StateService);
  private router = inject(Router);

  constructor() {
    this.subscription = this.stateService.getState().subscribe(state => {
      this.state = state;
    });
  }
  goToLogin() {
    this.router.navigate(['', 'login']);
  }

  goToSignUp() {
    this.router.navigate(['', 'signup']);
  }

  logOut() {
    this.stateService.setState(initial_state);
    localStorage.clear();
    this.router.navigate(['', 'login']);
  }

  gotoMyAccount() {
    console.log('myaccount')
    this.router.navigate(['', 'items']);
  }
}
