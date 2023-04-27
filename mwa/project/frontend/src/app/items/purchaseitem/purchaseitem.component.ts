import { Component, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { StateService } from 'src/app/state.service';
import { UserService } from 'src/app/user.service';
import { ISearch, ISearchItemRes, category, sortby, states } from 'src/app/usersmodel';
import { environment } from 'src/environments/environment';
import { toUrlPath } from '../../utils';
@Component({
  selector: 'app-purchaseitem',
  templateUrl: './purchaseitem.component.html',
  styleUrls: ['./purchaseitem.component.css']
})
export class PurchaseitemComponent {
  itemList: ISearchItemRes = { count: 0, page: 0, list: [] }

  private pageSize = 8
  totalPage: number = 1
  private subcription: Subscription[] = []
  private userService = inject(UserService)
  stateService = inject(StateService)
  searchOption: any = { user_id: '', pagesize: this.pageSize, page: 1 }

  constructor() {
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
    this.searchOption.user_id = this.stateService.getValue()._id
    this.subcription.push(this.userService.getPurchaseItem(this.searchOption).subscribe((res) => {
      if (res.success) {
        this.itemList.count = res.data.length
        this.itemList.page = 1;
        this.itemList.list = res.data
        console.log(res.data)
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
  toUrlPath(filename: string) {
    return toUrlPath(filename)
  }
}
