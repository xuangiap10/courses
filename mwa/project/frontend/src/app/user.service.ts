import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from 'src/environments/environment';
import { IProfile, IPurchaseItem, ISearch, ISearchItemFull, ISearchItemRes, IUser, SignUpUser } from './usersmodel';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _itemList = new BehaviorSubject<ISearchItemRes>({ count: 0, page: 0, list: [] })
  private http = inject(HttpClient);
  login(data: IUser) {
    //send email/password to backend
    return this.http.post<{ success: boolean, data: string }>(environment.HTTP_SERVER + "/users/login", data);
  }
  signup(data: SignUpUser) {
    //add info to backend
    return this.http.post<{ success: boolean, data: SignUpUser }>(environment.HTTP_SERVER + "/users/signup", data);
  }

  getProfile(user_id: string) {
    return this.http.get<{ success: boolean, data: SignUpUser }>(environment.HTTP_SERVER + "/users/" + user_id + "/profile");
  }

  updateProfile(data: IProfile, user_id: string) {
    return this.http.patch<{ success: boolean, data: any }>(environment.HTTP_SERVER + "/users/" + user_id + "/profile", data);
  }

  uploadImage(user_id: string, data: any) {
    return this.http.post<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id + '/images', data);
  }

  //--------------------------------
  getItemList() {
    return this._itemList.asObservable();
  }
  setItemList(new_data: ISearchItemRes) {
    this._itemList.next(new_data)
    return this._itemList.value;
  }

  purchaseItem(data: IPurchaseItem) {
    return this.http.patch<{ success: Boolean, data: any }>(environment.HTTP_SERVER + "/users/" + data.seller_id + "/items/purchase", data)
  }

  changePassword(data: { password: string, newpassword: string }, user_id: string) {
    return this.http.patch<{ success: Boolean, data: any }>(environment.HTTP_SERVER + "/users/" + user_id + "/profile/changepassword", data)
  }
  getPurchaseItem(data: any) {
    let queryParams = new HttpParams()
      .set('page', data.page)
      .set('pagesize', data.pagesize)
    return this.http.get<{ success: Boolean, data: any }>(environment.HTTP_SERVER + "/users/" + data.user_id + "/items/purchaselist", { params: queryParams })
  }
  getSearchItems(data: ISearch) {
    //let queryParams = new HttpParams();
    /*for (let param in data) {
      if (data.hasOwnProperty(param)) {
        queryParams.set(param, data[param])
      }
    }*/
    let queryParams = new HttpParams().set('state', data.state)
      .set('city', data.city)
      .set('category', data.category)
      .set('sortby', data.sortby)
      .set('status', data.status)
      .set('page', data.page)
      .set('pagesize', data.pagesize)
      .set('keyword', data.keyword)

    //console.log(queryParams)
    //console.log(data)
    return this.http.get<{ success: boolean, data: ISearchItemRes }>(environment.HTTP_SERVER + "/search", { params: queryParams });
  }

  getItemById(item_id: string) {
    return this.http.get<{ success: boolean, data: ISearchItemFull }>(environment.HTTP_SERVER + '/search/' + item_id);
  }
  constructor() { }
}

