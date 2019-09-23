package com.animals.care.animalscare.utils;

import java.util.Arrays;
import java.util.List;

import com.animals.care.animalscare.domain.Stock;
import com.animals.care.animalscare.dto.StockDTO;
import com.animals.care.animalscare.dto.StockDTO.StockProductDTO;

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

	public StockDTO cremeDTO() {
		return new StockDTO(STOCK_CREME_ID, new StockProductDTO(ProductUtils.INSTANCE.PRODUCT_CREME_ID.longValue()), STOCK_CREME_AMOUNT);
	}
	
	public StockDTO shamppoDTO() {
		return new StockDTO(STOCK_SHAMPPO_ID, new StockProductDTO(ProductUtils.INSTANCE.PRODUCT_SHAMPPO_ID.longValue()), STOCK_SHAMPPO_AMOUNT);
	}
	
	public Stock creme() {
		return new Stock(STOCK_CREME_ID, ProductUtils.INSTANCE.creme(), STOCK_CREME_AMOUNT);
	}

	public Stock shamppo() {
		return new Stock(STOCK_SHAMPPO_ID, ProductUtils.INSTANCE.shamppo(), STOCK_SHAMPPO_AMOUNT);
	}
}
