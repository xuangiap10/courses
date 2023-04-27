import { Component, ElementRef, OnDestroy, OnInit, ViewChild, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ItemService } from '../item.service';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { IItem, IItem2 } from '../itemsmodel';
import { Subscription } from 'rxjs';
import { StateService } from 'src/app/state.service';
import { category, status } from 'src/app/usersmodel';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit, OnDestroy {

  //@ViewChild('fileInput') fileInput!: ElementRef ;
  selectedFile!: File;

  private router = inject(Router);
  private itemService = inject(ItemService);
  private notification = inject(ToastrService);
  private activatedRoute = inject(ActivatedRoute);
  user_id: string = '';
  item_id: string = '';
  private subscription!: Subscription;
  private stateService = inject(StateService);
  upPictures: string[] = [];
  pictureNames = "";
  category: string[] = category;
  statuslist: string[] = status;


  UpdateForm = inject(NonNullableFormBuilder).group({
    title: ['', [Validators.required]],
    price: ['', [Validators.required]],
    status: ['', Validators.required],
    category: ['', [Validators.required]],
    pictures: ['', []],
    description: ['', []],
  })
  constructor() {
    this.subscription = this.stateService.getState().subscribe(state => {
      this.user_id = state._id;
    });
  }
  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.

    const item_id = this.activatedRoute.snapshot.paramMap.get('item_id') as string;
    this.item_id = item_id;
    this.itemService.getItemById(this.user_id, item_id).subscribe(res => {
      if (res.success) {
        const { title, price, status, category, pictures, description } = res.data;

        //this.UpdateForm.get('_id')?.patchValue(_id);
        this.UpdateForm.get('title')?.patchValue(title);
        this.UpdateForm.get('price')?.patchValue(price);
        this.UpdateForm.get('status')?.patchValue(status);
        this.UpdateForm.get('category')?.patchValue(category);
        //this.UpdateForm.get('pictures')?.patchValue(pictures);
        this.UpdateForm.get('description')?.patchValue(description);
        this.upPictures = pictures;
        this.pictureNames = this.upPictures.toString();
        /*pictures.forEach(value => {
          this.pictureNames = this.pictureNames + " " + value;
        })*/

      }
    });
  }
  ngOnDestroy(): void {
    //Called once, before the instance is destroyed.
    //Add 'implements OnDestroy' to the class.
    this.user_id = '';
    this.subscription.unsubscribe();

  }
  onUpdate() {
    //let item: IUpdate = this.UpdateForm.value as IUpdate;
    const user: IUpdate = { ...this.UpdateForm.value, status: "Active", pictures: this.upPictures } as IUpdate;
    //item.pictures = this.selectedFile.name;
    this.itemService.updateItem(this.user_id, { ...user, _id: this.item_id } as IItem2).subscribe(res => {
      if (res.success) {

        this.notification.success('Updated item successfully');
        this.router.navigate(['', 'items', this.item_id]);
      }
    })
  }
  onCancel() {
    this.router.navigate(['', 'items', 'listall']);
  }

  onUpload() {

    if (this.upPictures.includes(this.selectedFile.name)) {
      this.notification.info('Already Uploaded');
      return;
    }
    const formData = new FormData();
    formData.append('file', this.selectedFile, this.selectedFile.name);

    this.itemService.uploadImage(this.user_id, formData).subscribe(res => {
      if (res.success) {
        //this.upPictures.push(this.selectedFile.name);
        this.upPictures = [...this.upPictures, this.selectedFile.name];
        this.pictureNames = this.upPictures.toString();//this.pictureNames + " " + this.selectedFile.name;

      }

    })
  }
  handleFileSelect(event: any) {
    this.selectedFile = event.target.files[0];

  }

}

interface IUpdate {
  title: string,
  price: string,
  status: string,
  category: string,
  pictures: string[],
  description: string,

}
