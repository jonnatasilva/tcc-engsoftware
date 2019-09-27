import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { StockService } from 'src/app/provider/service/stock.service';

@Component({
  selector: 'app-stock-table',
  templateUrl: './stocks-table.component.html',
  styleUrls: ['./stocks-table.component.css']
})
export class StockTableComponent implements OnInit {

  displayedColumns: string[] = [ 'stockId', 'product', 'amount' ];

  dataSource = new MatTableDataSource();
  
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private stockService: StockService) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;

    this.stockService.findAllStocks()
    .subscribe(res => {
      this.dataSource.data = res;
    });
  }

}
