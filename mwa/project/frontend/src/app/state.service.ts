import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IState, initial_state } from './statesmodel';

@Injectable({
  providedIn: 'root'
})
export class StateService {

  private _state = new BehaviorSubject<IState>(initial_state);

  getState() {
    return this._state.asObservable();
  }
  setState(new_data: IState) {
    this._state.next(new_data);
    return this._state.value;
  }
  isLoggedIn() {
    return this._state.value._id ? true : false;
  }
  getToken() {
    return this._state.value.jwt || '';
  }
  getValue() {
    return this._state.value
  }
  constructor() { }
}
