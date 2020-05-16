import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingsService {

  private event = new EventEmitter()
  
  constructor() { }

  show() {
    this.event.emit({ activated: true });
  }

  hide() {
    this.event.emit({ activated: false });
  }

  listening() {
    return this.event;
  }
}
