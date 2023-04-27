import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddComponent } from './add/add.component';
import { UpdateComponent } from './update/update.component';
import { ListallComponent } from './listall/listall.component';
import { ListComponent } from './list/list.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MyaccountComponent } from './myaccount/myaccount.component';
import { ProfileComponent } from './profile/profile.component';
import { PurchaseitemComponent } from './purchaseitem/purchaseitem.component';




@NgModule({
  declarations: [
    AddComponent,
    UpdateComponent,
    ListallComponent,
    ListComponent,
    MyaccountComponent,
    ProfileComponent,
    PurchaseitemComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild([
      { path: '', pathMatch: 'full', redirectTo: '' },
      { path: 'profile', component: ProfileComponent },
      { path: 'listall', component: ListallComponent },
      { path: 'purchaseitem', component: PurchaseitemComponent },
      { path: 'add', component: AddComponent },
      { path: 'update/:item_id', component: UpdateComponent },
      { path: ':item_id', component: ListComponent },


    ]),

  ]
})
export class ItemsModule { }
