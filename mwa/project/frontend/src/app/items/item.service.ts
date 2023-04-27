import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from 'src/environments/environment';
import { IItem, IItem2 } from './itemsmodel';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private http = inject(HttpClient);
  constructor() { }
  getItems(user_id: string) {
    return this.http.get<{ success: boolean, data: IItem2[] }>(environment.HTTP_SERVER + '/users/' + user_id + '/items');
  }
  getItemById(user_id: string, item_id: string) {
    return this.http.get<{ success: boolean, data: IItem2 }>(environment.HTTP_SERVER + '/users/' + user_id + '/items/' + item_id);
  }

  addItem(user_id: string, item: IItem2) {
    return this.http.post<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id + '/items', item);
  }
  updateItem(user_id: string, item: IItem2) {
    return this.http.patch<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id + '/items/' + item._id, item);
  }
  deleteItem(user_id: string, item_id: string) {
    return this.http.delete<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id + '/items/' + item_id);
  }

  uploadPicture(user_id: string, item_id: string, data: any){
    return this.http.post<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id + '/items/' + item_id + '/pictures', data);
  }
  uploadImage(user_id: string,  data: any){
    return this.http.post<{ success: boolean, data: any }>(environment.HTTP_SERVER + '/users/' + user_id +  '/images', data);
  }
}
