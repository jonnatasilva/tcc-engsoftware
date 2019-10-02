import { Component, OnInit } from '@angular/core';
import { LoadingsService } from './service/loadings.service';

@Component({
  selector: 'app-loadings',
  templateUrl: './loadings.component.html',
  styleUrls: ['./loadings.component.css']
})
export class LoadingsComponent implements OnInit {

  show = false;
  message = 'Loading ...';

  constructor(private loadingService: LoadingsService) { }

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
