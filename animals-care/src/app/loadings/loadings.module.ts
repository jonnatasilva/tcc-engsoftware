import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadingsComponent } from './loadings.component';



@NgModule({
  declarations: [LoadingsComponent],
  imports: [
    CommonModule
  ],
  exports: [LoadingsComponent]
})
export class LoadingsModule { }
