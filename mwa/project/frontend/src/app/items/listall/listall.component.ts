import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ItemService } from '../item.service';
import { IItem, IItem2 } from '../itemsmodel';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { StateService } from 'src/app/state.service';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-listall',
  templateUrl: './listall.component.html',
  styleUrls: ['./listall.component.css']
})
export class ListallComponent implements OnInit, OnDestroy {

  private stateService = inject(StateService);
  private subscription!: Subscription;

  private notification = inject(ToastrService);
  private router = inject(Router);
  private itemService = inject(ItemService);
  items!: IItem2[];
  user_id: string = '';
  urlpath: string = environment.HTTP_SERVER + '/images/';
  constructor() {

    this.subscription = this.stateService.getState().subscribe(state => {
      this.user_id = state._id;
    });
  }
  ngOnInit(): void {
    this.itemService.getItems(this.user_id).subscribe(res => {
      if (res.success) {
        this.items = res.data;
      }
    })
  }
  ngOnDestroy(): void {
    this.user_id = '';
    this.subscription.unsubscribe();
  }
  goToAdd() {
    this.router.navigate(['', 'items', 'add']);
  }
  goToUpdate(item_id: string) { //[routerLink]="['','items','update', item._id]
    this.router.navigate(['', 'items', 'update', item_id]);
  }

  onDeleteItem(item_id: string) {

    this.itemService.deleteItem(this.user_id, item_id).subscribe(res => {
      if (res.success) {
        this.notification.success("Deleted Item Successfully");
        this.items = this.items.filter(item => item._id !== item_id);
      }
    });
  }
  getFullPath(data: string[]): string {
    let ret = "";
    if (data.length > 0)
      ret = environment.HTTP_SERVER + "/images/" + data[0];
    return ret;
  }
}
