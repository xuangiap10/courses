import { Component, OnDestroy, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';
import { IPurchaseItem, ISearchItemFull } from '../usersmodel';
import { Subscription } from 'rxjs';
import { environment } from 'src/environments/environment';
import { StateService } from '../state.service';
import { ToastrService } from 'ngx-toastr';
import { toUrlPath } from '../utils';

@Component({
  selector: 'app-searchitem',
  templateUrl: './searchitem.component.html',
  styleUrls: ['./searchitem.component.css']
})
export class SearchitemComponent implements OnDestroy {

  private route = inject(ActivatedRoute);
  private item_id = this.route.snapshot.paramMap.get('item_id') as string
  private userService = inject(UserService)
  private subcription: Subscription[] = []
  private router = inject(Router)
  private notif = inject(ToastrService);
  stateService = inject(StateService)
  item!: ISearchItemFull
  descriptions: string[] = []
  pic: string = ''

  constructor() {
    console.log('item_id' + this.item_id)
    this.loadItem()
  }

  loadItem() {
    this.subcription.push(this.userService.getItemById(this.item_id).subscribe((res) => {
      if (res.success) {
        this.item = res.data
        this.pic = this.item.items.pictures[0] ?? ''
        this.descriptions = this.item.items.description.split('\n');
      }
    }))
  }

  ngOnDestroy(): void {
    this.subcription.forEach(s => s.unsubscribe())
  }
  choosePic(e: any) {
    //console.log(e)
    //console.log('id ' + e.target.id)
    this.pic = this.item.items.pictures[e.target.id];
  }
  onPurchase() {
    if (this.stateService.isLoggedIn())
      this.purchase(this.item_id)
    else {
      this.notif.success("You need to login to purchase");
      //this.router.navigate(['', 'login'])
    }
  }

  purchase(item_id: string) {
    const option: IPurchaseItem = {
      item_id: item_id,
      seller_id: this.item._id,
      buyer_id: this.stateService.getValue()._id,
      buyer_email: this.stateService.getValue().email,
      buyer_fullname: this.stateService.getValue().fullname
    }

    //console.log(this.itemList.list)
    //console.log(option)
    this.subcription.push(this.userService.purchaseItem(option).subscribe((res) => {
      if (res.success) {
        console.log('purchase successful')
        this.notif.success('Purchase successfully')
        this.loadItem()
      }
    }))
  }
  toUrlPath(filename: string) {
    return toUrlPath(filename)
  }
}

