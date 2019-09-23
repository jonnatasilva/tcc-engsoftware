package com.animals.care.animalscare.service;

import java.util.List;

import com.animals.care.animalscare.dto.ProductDTO;
import com.animals.care.animalscare.dto.ProductFilter;

/**
 * @author jonnatas
 *
 */
public interface IProductService {

	ProductDTO save(ProductDTO product);
	List<ProductDTO> findAll(ProductFilter productFilter);
	ProductDTO findByProductId(Long productId);
	ProductDTO updateProduct(Long productId, ProductDTO product);
	void deleteByProductId(Long productId);
}
