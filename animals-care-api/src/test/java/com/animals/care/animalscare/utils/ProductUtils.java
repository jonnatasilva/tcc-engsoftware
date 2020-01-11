package com.animals.care.animalscare.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.animals.care.animalscare.domain.Manufacturer;
import com.animals.care.animalscare.domain.Product;
import com.animals.care.animalscare.dto.ManufacturerDTO;
import com.animals.care.animalscare.dto.ProductDTO;

public enum ProductUtils {

	INSTANCE;
	
	public static Integer MANUFACTURER_LOREAL_ID = 1;
	public static String MANUFACTURER_LOREAL_NAME = "L'Oréal";
	
	public static Integer MANUFACTURER_SANOL_ID = 1;
	public static String MANUFACTURER_SANOL_NAME = "Sanol";

	public static Integer PRODUCT_CREME_ID = 2;
	public static String PRODUCT_CREME_NAME = "Creme";

	public static Integer PRODUCT_SHAMPPO_ID = 1;
	public static String PRODUCT_SHAMPPO_NAME = "Shamppo";
	
	private static final LocalDateTime PRODUCT__CREATEAT = LocalDateTime.now();
	
	public List<ProductDTO> productsDTO() {
		return Arrays.asList(shamppoDTO(), cremeDTO());
	}
	
	public List<Product> products() {
		return Arrays.asList(shamppo(), creme());
	}

	public ProductDTO cremeDTO() {
		ProductDTO creme = new ProductDTO();
		creme.setProductId(PRODUCT_CREME_ID.longValue());
		creme.setName(PRODUCT_CREME_NAME);
		creme.setPrice(BigDecimal.TEN.setScale(2));
		creme.setSpecifications("Specifications");
		creme.setManufacturer(lorealDTO());
		creme.setCreatedAt(PRODUCT__CREATEAT);
		return creme;
	}
	
	public Product creme() {
		Product creme = new Product();
		creme.setProductId(PRODUCT_CREME_ID.longValue());
		creme.setName(PRODUCT_CREME_NAME);
		creme.setPrice(BigDecimal.TEN.setScale(2));
		creme.setSpecifications("Specifications");
		creme.setManufacturer(loreal());
		creme.setCreatedAt(PRODUCT__CREATEAT);
		return creme;
	}

	public ProductDTO shamppoDTO() {
		ProductDTO shamppo = new ProductDTO();
		shamppo.setProductId(PRODUCT_SHAMPPO_ID.longValue());
		shamppo.setName(PRODUCT_SHAMPPO_NAME);
		shamppo.setPrice(BigDecimal.valueOf(20).setScale(2));
		shamppo.setSpecifications("Indicate: Dog, Cat, Breed: Small Breeds, Medium Breeds, Large Breeds");
		shamppo.setManufacturer(lorealDTO());
		shamppo.setCreatedAt(PRODUCT__CREATEAT);
		return shamppo;
	}
	
	public Product shamppo() {
		Product shamppo = new Product();
		shamppo.setProductId(PRODUCT_SHAMPPO_ID.longValue());
		shamppo.setName(PRODUCT_SHAMPPO_NAME);
		shamppo.setPrice(BigDecimal.valueOf(20).setScale(2));
		shamppo.setSpecifications("Indicate: Dog, Cat, Breed: Small Breeds, Medium Breeds, Large Breeds");
		shamppo.setManufacturer(loreal());
		shamppo.setCreatedAt(PRODUCT__CREATEAT);
		return shamppo;
	}

	public ManufacturerDTO lorealDTO() {
		ManufacturerDTO loreal = new ManufacturerDTO();
		loreal.setManufacturerId(MANUFACTURER_LOREAL_ID.longValue());
		loreal.setName(MANUFACTURER_LOREAL_NAME);
		return loreal;
	}
	
	public Manufacturer loreal() {
		Manufacturer loreal = new Manufacturer();
		loreal.setManufacturerId(MANUFACTURER_LOREAL_ID.longValue());
		loreal.setName(MANUFACTURER_LOREAL_NAME);
		return loreal;
	}
	
	public ManufacturerDTO sanolDTO() {
		ManufacturerDTO loreal = new ManufacturerDTO();
		loreal.setManufacturerId(MANUFACTURER_SANOL_ID.longValue());
		loreal.setName(MANUFACTURER_SANOL_NAME);
		return loreal;
	}
	
	public Manufacturer sanol() {
		Manufacturer loreal = new Manufacturer();
		loreal.setManufacturerId(MANUFACTURER_SANOL_ID.longValue());
		loreal.setName(MANUFACTURER_SANOL_NAME);
		return loreal;
	}
}
