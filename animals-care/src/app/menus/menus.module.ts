import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MenusRoutingModule } from './menus-routing.module';
import { MenusComponent } from './menus.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';


@NgModule({
  declarations: [MenusComponent],
  imports: [
    CommonModule,
    MenusRoutingModule,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatButtonModule,
    MatMenuModule
  ]
})
export class MenusModule { }
