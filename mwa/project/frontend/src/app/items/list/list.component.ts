import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService } from '../item.service';
import { StateService } from 'src/app/state.service';
import { Subscription } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { IItem, IItem2, initial_item } from '../itemsmodel';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {


  item_id: string = '';
  private router = inject(Router);
  private itemService = inject(ItemService);
  private activatedRoute = inject(ActivatedRoute);

  private stateService = inject(StateService);
  private subscription!: Subscription;

  private notification = inject(ToastrService);

  user_id: string = '';
  item: IItem2 = initial_item;
  pic: string = '';
  descriptions: string[] = [];

  constructor() {
    this.item_id = this.activatedRoute.snapshot.paramMap.get('item_id') as string;
    this.subscription = this.stateService.getState().subscribe(state => {
      this.user_id = state._id;
    });
  }
  ngOnInit(): void {
    this.itemService.getItemById(this.user_id, this.item_id).subscribe(res => {
      if (res.success) {
        this.item = res.data;
        this.pic = this.getFullPath(this.item.pictures);
        this.descriptions = this.item.description.split('\n');
      }
    })
  }
  goToUpdate(item_id: string) { //[routerLink]="['','items','update', item._id]
    this.router.navigate(['', 'items', 'update', item_id]);
  }

  onDeleteItem(item_id: string) {

    this.itemService.deleteItem(this.user_id, item_id).subscribe(res => {
      if (res.success) {
        this.notification.success("Deleted Item Successfully");
        this.router.navigate(['', 'items', 'listall']);
      }
    });
  }
  getPath(data: string){
    let ret = "";
    if (data.length > 0)
      ret = environment.HTTP_SERVER + "/images/" + data;
    return ret;
  }
  getFullPath(data: string[]): string {
    let ret = "";
    if (data.length > 0)
      ret = environment.HTTP_SERVER + "/images/" + data[0];
    return ret;
  }
  ngOnDestroy(): void {
    this.user_id = '';
    this.subscription.unsubscribe();
  }
  choosePic(e: any) {
    //console.log(e)
    //console.log('id ' + e.target.id)
    this.pic = environment.HTTP_SERVER + "/images/" + this.item.pictures[e.target.id];
  }
}
