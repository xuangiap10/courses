import { Component, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { SignUpUser, initial_addr, states } from '../usersmodel'
import { ToastrService } from 'ngx-toastr';
import { isPasswordValid, isPhoneValid, isZipCodeValid, toUrlPath } from '../utils';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  states: { state: string, city: string[] }[] = states;
  cities: string[] = states[0].city;
  avatar_file: string = "";
  upPictures = "";
  selectedFile!: File;
  mismatch = false;
  wrongpassword = false;
  private router = inject(Router);
  private userService = inject(UserService);
  private notification = inject(ToastrService);
  signUpForm = inject(NonNullableFormBuilder).group({

    fullname: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, isPasswordValid]],
    confirmpassword: ['', [Validators.required]],
    phone: ['', [Validators.required, isPhoneValid]],
    avatar: '',
    street: [''],
    city: ['', [Validators.required]],
    state: ['', [Validators.required]],
    zipcode: ['', [Validators.required, isZipCodeValid]]
  })

  get fullname() { return this.signUpForm.get('fullname') as FormControl; }
  get email() { return this.signUpForm.get('email') as FormControl; }
  get password() { return this.signUpForm.get('password') as FormControl; }
  get confirmpassword() { return this.signUpForm.get('confirmpassword') as FormControl; }
  get phone() { return this.signUpForm.get('phone') as FormControl; }
  get avatar() { return this.signUpForm.get('avatar') as FormControl; }
  get street() { return this.signUpForm.get('street') as FormControl; }
  get city() { return this.signUpForm.get('city') as FormControl; }
  get state() { return this.signUpForm.get('state') as FormControl; }
  get zipcode() { return this.signUpForm.get('zipcode') as FormControl; }

  backToHome() {

  }

  onSignUp() {

    if (this.password.value !== this.confirmpassword.value) {
      this.mismatch = true;
      return;
    }
    const formuser = this.signUpForm.value as unknown as ISignUp;

    const user: SignUpUser = {
      fullname: formuser.fullname,
      email: formuser.email,
      password: formuser.password,
      address: {
        street: formuser.street,
        city: formuser.city,
        state: formuser.state,
        zipcode: Number(formuser.zipcode)
      },
      phone: formuser.phone,
      avatar: this.upPictures
    };

    this.userService.signup(user).subscribe(res => {
      if (res.success) {
        this.notification.success('Successfully Signed Up');
        this.router.navigate(['', 'login']);
      }
    })
  }
  onCancel() {
    this.router.navigate(['']);
  }
  onBlur(event: any) {
    if (this.password.value !== this.confirmpassword.value) {
      this.mismatch = true;
    } else {
      this.mismatch = false;
    }
  }
  onKeyUp(event: any) {
    if (this.password.value !== this.confirmpassword.value) {
      this.mismatch = true;
    } else {
      this.mismatch = false;
    }
  }

  onPasswordKeyUp(event: any) {
    if (isPasswordValid(this.password)) {
      this.wrongpassword = true;
    } else {
      this.wrongpassword = false;
    }
  }
  updateCity(event: any) {
    const value: string = this.state.value;
    this.cities = states.find((s) => s.state == value)?.city ?? states[0].city;
    this.signUpForm.get('city')?.setValue(this.cities[0])
  }
  onUpload() {

    if (this.upPictures !== "" && this.upPictures === this.selectedFile.name) {
      this.notification.info('Already Uploaded');
      return;
    }
    const formData = new FormData();
    formData.append('file', this.selectedFile, this.selectedFile.name);
    console.log(formData);
    this.userService.uploadImage("123", formData).subscribe(res => {
      if (res.success) {
        this.upPictures = this.selectedFile.name;
        this.notification.success('Upload successfully');
        this.avatar_file = toUrlPath(this.upPictures);
      }

    })
  }
  handleFileSelect(event: any) {
    this.selectedFile = event.target.files[0];
    //this.selectedFile = <File>this.fileInput.nativeElement.files[0];
    //console.log(this.selectedFile);
  }
}

interface ISignUp {
  fullname: string,
  email: string,
  password: string,
  confirmpassword: string,
  phone: string,
  avatar: string,
  street: string,
  city: string,
  state: string,
  zipcode: string
}