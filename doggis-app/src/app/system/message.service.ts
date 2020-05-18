import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private _snackBar: MatSnackBar) { }

  showMessage(message: string) {
    this._snackBar.open(message, 'Ok', {
      verticalPosition: 'top',
      duration: 2000
    });
  }
}
