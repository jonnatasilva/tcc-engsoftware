import { Component, OnInit, EventEmitter, OnDestroy } from '@angular/core';
import { LoadingService } from '../provider/loading/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent implements OnInit, OnDestroy {

  show = false;
  message = 'Loading ...';

  constructor(private loadingService: LoadingService) { }

  ngOnInit() {
    this.loadingService.listening().subscribe(event => {
      this.show = event.activated;
      this.message = event.message ? event.message : this.message;
    });
  }

  ngOnDestroy() {
    this.loadingService.listening().unsubscribe();
  }
}
