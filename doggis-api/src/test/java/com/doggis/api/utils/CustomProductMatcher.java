package com.doggis.api.utils;

import org.mockito.ArgumentMatcher;

import com.doggis.api.domain.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomProductMatcher implements ArgumentMatcher<Product> {

	private final Product left;
	
	@Override
	public boolean matches(Product right) {
		return left.getProductId().equals(right.getProductId())
				&& left.getName().equals(right.getName())
				&& left.getManufacturer().equals(right.getManufacturer())
				&& left.getSpecifications().equals(right.getSpecifications())
				&& left.getCreatedAt().toLocalDate().equals(right.getCreatedAt().toLocalDate())
				&& left.getUpdatedAt().toLocalDate().equals(right.getUpdatedAt().toLocalDate());
	}

}
