import { Component, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IUser } from '../usersmodel';
import { StateService } from '../state.service';
import jwt_decode from 'jwt-decode'
import { Subscription } from 'rxjs';
import { IState } from '../statesmodel';
import { isPasswordValid } from '../utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  wrongpassword = false;
  private router = inject(Router);
  private dataService = inject(UserService);
  private notofication = inject(ToastrService);
  private stateService = inject(StateService);
  private subscription!: Subscription;
  LogInForm = inject(NonNullableFormBuilder).group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, isPasswordValid]]
  })
  get email() { return this.LogInForm.get('email') as FormControl; }
  get password() { return this.LogInForm.get('password') as FormControl; }
  constructor() {
    this.subscription = this.stateService.getState().subscribe(state => {
      if (state._id) this.router.navigate(['']);
    })
  }

  onKeyUp(event: any) {
    if (isPasswordValid(this.password)) {
      this.wrongpassword = true;
    } else {
      this.wrongpassword = false;
    }
  }
  ngOnDestroy(): void {

    this.subscription.unsubscribe();
  }
  onLogIn() {
    const user: IUser = { email: this.email.value, password: this.password.value, _id: '', fullname: '' };
    this.dataService.login(user).subscribe(res => {
      if (res.success) {
        this.notofication.success("Logged In Successfully");
        const encrypted_token = res.data;
        const decoded_token = jwt_decode(encrypted_token) as IState;
        const state = { ...decoded_token, jwt: encrypted_token } as IState;
        this.stateService.setState(state);
        localStorage.setItem('APP_STATE', JSON.stringify(state));
        //this.router.navigate(['','items']);//display all items in
        this.router.navigate(['']);
      }
    })
  }
  onCancel() {
    this.router.navigate(['']);
  }
}


