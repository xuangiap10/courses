import { APP_INITIALIZER, NgModule, inject } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { StateService } from './state.service';
import { ItemsModule } from './items/items.module';
import { AddTokenInterceptor } from './add-token.interceptor';
import { SearchitemsComponent } from './searchitems/searchitems.component';
import { SearchitemComponent } from './searchitem/searchitem.component';

function bootstrap(stateService: StateService) {
  return () => {
    const persisted_state = localStorage.getItem('APP_STATE');
    if (persisted_state) {
      stateService.setState(JSON.parse(persisted_state));
    }
  }
}
@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    LoginComponent,
    SearchitemsComponent,
    SearchitemComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', pathMatch: 'full', redirectTo: 'search' },
      { path: 'login', component: LoginComponent },
      { path: 'signup', component: SignupComponent },
      { path: 'search', component: SearchitemsComponent },
      { path: 'search/:item_id', component: SearchitemComponent },
      {
        path: 'items',
        loadChildren: () => import('./items/items.module').then(module => module.ItemsModule),
        canActivate: [() => inject(StateService).isLoggedIn()]
      },
      { path: '**', redirectTo: '' }
    ]),
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    { provide: APP_INITIALIZER, useFactory: bootstrap, deps: [StateService], multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: AddTokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
