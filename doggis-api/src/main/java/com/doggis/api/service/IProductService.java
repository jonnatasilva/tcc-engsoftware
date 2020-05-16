package com.doggis.api.service;

import java.util.List;

import com.doggis.api.dto.ProductDTO;
import com.doggis.api.dto.ProductFilter;

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
