import { Component, OnDestroy, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Subscription } from 'rxjs';
import { StateService } from 'src/app/state.service';
import { IState } from 'src/app/statesmodel';
import { UserService } from 'src/app/user.service';
import { IProfile, SignUpUser, initial_addr } from 'src/app/usersmodel';
import { states } from 'src/app/usersmodel';
import { environment } from 'src/environments/environment';
import { isPasswordValueValid, isPhoneValueValid, isZipCodeValueValid, toUrlPath } from '../../utils';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnDestroy {
  private dataService = inject(StateService);
  private userService = inject(UserService);
  private subcription: Subscription[] = []
  private router = inject(Router);
  private notif = inject(ToastrService);
  state!: IState
  states = states;
  cities: string[] = states[1].city
  userInfo!: SignUpUser
  profileForm = inject(NonNullableFormBuilder).group({
    fullname: ['', [Validators.required]],
    email: ['', [Validators.required]],
    password: '',
    newpassword: '',
    repassword: '',
    street: '',
    city: ['', [Validators.required]],
    state: ['', [Validators.required]],
    phone: ['', [Validators.required]],
    zipcode: [0, [Validators.required]],
    avatar: ''
  })

  constructor() {
    this.state = this.dataService.getValue()
    this.subcription.push(this.userService.getProfile(this.state._id).subscribe((res) => {
      if (res.success) {

        this.userInfo = res.data
        this.setValue(this.userInfo)
        this.cities = states.find((s) => s.state == this.userInfo.address.state)?.city ?? states[0].city;
        //console.log(this.userInfo)
      }
    }))
  }

  stateChange() {
    console.log('state change')
    this.cities = states.find((s) => s.state == this.profileForm.value.state)?.city ?? states[0].city;
    this.profileForm.get('city')?.setValue(this.cities[0])
  }

  setValue(data: SignUpUser) {

    this.profileForm.get('email')?.setValue(this.userInfo.email);
    this.profileForm.get('fullname')?.setValue(this.userInfo.fullname);
    this.profileForm.get('street')?.setValue(this.userInfo.address.street);
    this.profileForm.get('state')?.setValue(this.userInfo.address.state);
    this.profileForm.get('city')?.setValue(this.userInfo.address.city);
    this.profileForm.get('zipcode')?.setValue(this.userInfo.address.zipcode);
    this.profileForm.get('phone')?.setValue(this.userInfo.phone);
  }
  ngOnDestroy(): void {
    this.subcription.forEach(s => s.unsubscribe())
  }

  updateProfile() {
    const data: IProfile = {
      fullname: this.profileForm.value.fullname ?? '',
      address: {
        street: this.profileForm.value.street ?? '',
        city: this.profileForm.value.city ?? '',
        state: this.profileForm.value.state ?? '',
        zipcode: this.profileForm.value.zipcode ?? 0
      },
      phone: this.profileForm.value.phone ?? '',
      avatar: ''
    }
    this.subcription.push(this.userService.updateProfile(data, this.state._id).subscribe((res) => {
      if (res.success) {
        this.notif.success('Save successfully');
        //this.router.navigate([''])
      }
    }))
  }

  cancel() {
    this.setValue(this.userInfo)
    this.router.navigate([''])
  }
  changePassword() {
    console.log('change password')
    if (!this.profileForm.value.password) {
      this.notif.error('Password can not be empty')
      return
    }
    if (!isPasswordValueValid(this.profileForm.value.newpassword as string)) {
      this.notif.error('New Password length should be at least 6 ')
      return
    }
    if (this.profileForm.value.newpassword != this.profileForm.value.repassword) {
      this.notif.error('New Password not match')
      return
    }

    const data = {
      password: this.profileForm.value.password as string,
      newpassword: this.profileForm.value.newpassword as string
    }
    this.subcription.push(this.userService.changePassword(data, this.state._id).subscribe((res) => {
      if (res.success) {
        this.notif.success('Password changed successfully')
        console.log('change password OK')
      }
    }))
  }

  toUrlPath(filename: string) {
    //console.log(toUrlPath(filename))
    return toUrlPath(filename)
  }
}
