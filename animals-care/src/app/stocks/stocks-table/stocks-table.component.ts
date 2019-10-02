import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { StocksService } from '../service/stocks.service';

@Component({
  selector: 'app-stock-table',
  templateUrl: './stocks-table.component.html',
  styleUrls: ['./stocks-table.component.css']
})
export class StockTableComponent implements OnInit {

  displayedColumns: string[] = [ 'stockId', 'product', 'amount' ];

  dataSource = new MatTableDataSource();
  
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private stockService: StocksService) { }

  ngOnInit() {
    this.dataSource.sort = this.sort;

    this.stockService.findAllStocks()
    .subscribe(res => {
      this.dataSource.data = res;
    });
  }

}
