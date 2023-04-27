import { Component, OnDestroy, inject } from '@angular/core';
import { states, category, sortby, ISearch, status, ISearchItemRes, ISearchItemFull } from '../usersmodel'
import { NonNullableFormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserService } from '../user.service';
import { StateService } from '../state.service';
import { Router, RouterLink } from '@angular/router';
import { IPurchaseItem } from '../usersmodel'
import { environment } from 'src/environments/environment';
import { ToastrService } from 'ngx-toastr';
import { toUrlPath } from '../utils';

@Component({
  selector: 'app-searchitems',
  templateUrl: './searchitems.component.html',
  styleUrls: ['./searchitems.component.css']
})
export class SearchitemsComponent implements OnDestroy {
  states: { state: string, city: string[] }[] = states
  cities: string[] = states[0].city
  category: string[] = category
  sortby: string[] = sortby
  status: string[] = status
  itemList: ISearchItemRes = { count: 0, page: 0, list: [] }

  stateForm = inject(NonNullableFormBuilder).group({
    state: states[0].state,
    city: states[0].city[0],
    category: category[0],
    status: status[0],
    sortby: sortby[0],
    keyword: ''
  })

  private pageSize = 8
  totalPage: number = 1
  private subcription: Subscription[] = []
  searchOption: ISearch = { state: states[0].state, city: states[0].city[0], status: status[0], category: category[0], keyword: '', sortby: sortby[0], page: 1, pagesize: this.pageSize }
  private router = inject(Router)
  private userService = inject(UserService)
  stateService = inject(StateService)
  private notif = inject(ToastrService);

  constructor() {
    this.subcription.push(this.stateForm.get('state')?.valueChanges.subscribe((value) => {
      this.cities = states.find((s) => s.state == value)?.city ?? states[0].city;
      this.stateForm.get('city')?.setValue(this.cities[0])
    }) as Subscription)

    this.subcription.push(this.stateForm.valueChanges.subscribe((value) => {
      this.searchOption = value as ISearch
      console.log(this.searchOption)
    }))

    this.subcription.push(
      this.userService.getItemList().subscribe((value) => {
        this.itemList = value
        this.totalPage = Math.floor((this.itemList.count + this.pageSize - 1) / this.pageSize)
      })
    )
    this.search();
  }
  ngOnDestroy(): void {
    this.subcription.forEach(s => s.unsubscribe());
  }
  search() {
    this.searchOption.pagesize = this.pageSize
    this.searchOption.page = 1;
    this.searchByOption()
  }
  searchByOption() {
    this.subcription.push(this.userService.getSearchItems(this.searchOption).subscribe((res) => {
      if (res.success) {

        if (res.data.count) this.userService.setItemList(res.data)
        else {
          this.notif.success("No result found")
          this.userService.setItemList({ count: 0, page: 0, list: [] })
        }
      }
    }))
  }

  isBackValid() {
    return (this.itemList.page > 1)
  }
  previous() {
    if (this.itemList.page <= 1) return;
    console.log('previous')
    this.searchOption.page--
    this.searchByOption()
  }
  next() {
    if (this.itemList.page >= this.totalPage) return;
    console.log('next')
    this.searchOption.page++
    this.searchByOption()
  }
  onPurchase(e: any) {
    if (this.stateService.isLoggedIn())
      this.purchase(e.target.id)
    else {
      this.notif.success("You need to login to purchase");
      //this.router.navigate(['', 'login'])
    }
  }
  purchase(item_id: string) {
    const option: IPurchaseItem = {
      item_id: item_id,
      seller_id: this.itemList.list.find((i) => i.items._id == item_id)?._id as string,
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
        this.search()
      } else {
        this.notif.error('Purchase failed')
      }

    }))
  }
  toUrlPath(filename: string) {
    return toUrlPath(filename)
  }
}
