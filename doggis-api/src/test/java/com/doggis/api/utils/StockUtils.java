package com.doggis.api.utils;

import java.util.Arrays;
import java.util.List;

import com.doggis.api.domain.Stock;
import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockDTO.StockProductDTO;
import com.doggis.api.response.StockResponse;

public enum StockUtils {
	
	INSTANCE;
	
	public static final int STOCK_SHAMPPO_AMOUNT = 10;
	public static final long STOCK_SHAMPPO_ID = 2L;
	
	public static final int STOCK_CREME_AMOUNT = 10;
	public static final long STOCK_CREME_ID = 1L;

	public List<StockDTO> createStocksDTO() {
		return Arrays.asList(shamppoDTO(), cremeDTO());
	}
	
	public List<Stock> createStocks() {
		return Arrays.asList(shamppo(), creme());
	}
	
	public List<StockResponse> createStocksResponse() {
		return Arrays
				.asList(shamppoResponse(), cremeResponse());
	}

	public StockDTO cremeDTO() {
		return new StockDTO(STOCK_CREME_ID, new StockProductDTO(ProductUtils.PRODUCT_CREME_ID.longValue()), STOCK_CREME_AMOUNT);
	}
	
	public StockDTO shamppoDTO() {
		return new StockDTO(STOCK_SHAMPPO_ID, new StockProductDTO(ProductUtils.PRODUCT_SHAMPPO_ID.longValue()), STOCK_SHAMPPO_AMOUNT);
	}
	
	public Stock creme() {
		return new Stock(STOCK_CREME_ID, ProductUtils.INSTANCE.creme(), STOCK_CREME_AMOUNT);
	}

	public Stock shamppo() {
		return new Stock(STOCK_SHAMPPO_ID, ProductUtils.INSTANCE.shamppo(), STOCK_SHAMPPO_AMOUNT);
	}
	
	public StockResponse cremeResponse() {
		return new StockResponse(STOCK_CREME_ID, ProductUtils.INSTANCE.creme(), STOCK_CREME_AMOUNT);
	}
	
	public StockResponse shamppoResponse() {
		return new StockResponse(STOCK_SHAMPPO_ID, ProductUtils.INSTANCE.shamppo(), STOCK_SHAMPPO_AMOUNT);
	}
}
