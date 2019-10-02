import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginsRoutingModule } from './logins-routing.module';
import { LoginsComponent } from './logins.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [LoginsComponent],
  imports: [
    CommonModule,
    LoginsRoutingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ]
})
export class LoginsModule {
}
