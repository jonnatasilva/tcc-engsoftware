package com.animals.care.animalscare.utils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.animals.care.animalscare.domain.Product;
import com.animals.care.animalscare.domain.Sale;
import com.animals.care.animalscare.domain.SaleProduct;
import com.animals.care.animalscare.dto.SaleDTO;
import com.animals.care.animalscare.dto.SaleDTO.SaleProductDTO;

public enum SaleUtils {
	INSTANCE;
	
	public static final long SALE_1_ID = 1l;
	public static final int SALE_1_AMOUNT = 2;
	
	public static final LocalDateTime saleDate = LocalDateTime.now();
	
	public Sale newSale() {
		Set<SaleProduct> saleProduct = new HashSet<>();
		saleProduct.add(new SaleProduct(SALE_1_AMOUNT, new Product(ProductUtils.PRODUCT_SHAMPPO_ID.longValue())));
		return new Sale(0L, saleDate, saleProduct);
	}
	
	public Sale saleCreated() {
		Set<SaleProduct> saleProduct = new HashSet<>();
		saleProduct.add(new SaleProduct(SALE_1_AMOUNT, new Product(ProductUtils.PRODUCT_SHAMPPO_ID.longValue())));
		return new Sale(SALE_1_ID, saleDate, saleProduct);
	}
	
	public SaleDTO saleDTO() {
		Set<SaleProductDTO> saleProduct = new HashSet<>();
		saleProduct.add(new SaleProductDTO(ProductUtils.PRODUCT_SHAMPPO_ID.longValue(), 2));
		return new SaleDTO(null, saleDate, saleProduct);
	}
}
