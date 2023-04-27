import { Component, ElementRef, OnDestroy, ViewChild, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { ItemService } from '../item.service';
import { IItem2 } from '../itemsmodel';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { StateService } from 'src/app/state.service';
import { Subscription } from 'rxjs';
import { category } from 'src/app/usersmodel';


@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnDestroy {

  //@ViewChild('fileInput') fileInput!: ElementRef;
  selectedFile!: File;

  user_id: string = '';
  private router = inject(Router);
  private itemService = inject(ItemService);
  private notification = inject(ToastrService);
  AddForm = inject(NonNullableFormBuilder).group({
    title: ['', [Validators.required]],
    price: ['', [Validators.required]],
    category: ['', [Validators.required]],
    pictures: ['', []],
    description: ['',[]],
  })
  private subscription!: Subscription;
  private stateService = inject(StateService);
  upPictures: string[] = [];
  pictureNames: string = "";
  category: string[] = category;

  get pictures() { return this.AddForm.get('pictures') as FormControl; }
  onAdd() {
    const user: IAdd = { ...this.AddForm.value, status: "Active", pictures: this.upPictures } as IAdd;
    //console.log(user);
    //console.log("onAdd: " + user.pictures);
    //console.log("onAdd: " + this.AddForm.get('pictures')?.value)

    this.itemService.addItem(this.user_id, user as IItem2).subscribe(res => {
      if (res.success) {
        this.notification.success('Added item successfully');
        this.router.navigate(['', 'items', 'listall']);
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
    //console.log(formData);
    this.itemService.uploadImage(this.user_id, formData).subscribe(res => {
      if (res.success) {
        //this.upPictures.push(this.selectedFile.name);
        this.upPictures = [...this.upPictures, this.selectedFile.name];
        this.pictureNames = this.upPictures.toString();


        this.notification.success('Upload successfully');
      }

    })
  }
  handleFileSelect(event: any) {
    this.selectedFile = event.target.files[0];
    //this.selectedFile = <File>this.fileInput.nativeElement.files[0];
    //console.log(this.selectedFile);
  }



  constructor() {
    this.subscription = this.stateService.getState().subscribe(state => {
      this.user_id = state._id;
    });
  }
  ngOnDestroy(): void {
    //Called once, before the instance is destroyed.
    //Add 'implements OnDestroy' to the class.
    this.user_id = '';
    this.subscription.unsubscribe();

  }
}

interface IAdd {
  title: string,
  price: string,
  status: string,
  category: string,
  pictures: string[],
  description: string,

}
