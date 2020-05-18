package com.doggis.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doggis.api.domain.Product;
import com.doggis.api.domain.QProduct;
import com.doggis.api.dto.ProductDTO;
import com.doggis.api.dto.ProductFilter;
import com.doggis.api.exception.CustomException;
import com.doggis.api.repository.IProductRepository;
import com.doggis.api.service.IProductService;
import com.hotels.beans.BeanUtils;
import com.hotels.transformer.model.FieldTransformer;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class ProductServiceImpl implements IProductService {

	private static final BeanUtils BEANS_UTILS = new BeanUtils();
	
	@Autowired
	private IProductRepository productRepository;

	@Override
	public List<ProductDTO> findAll(ProductFilter productFilter) {
		if (productFilter != null && StringUtils.isNotEmpty(productFilter.getName())) {
			BooleanExpression predicate = QProduct.product
					.name
					.containsIgnoreCase(productFilter.getName());
			
			return StreamSupport.stream(productRepository.findAll(predicate).spliterator(), false)
					.map(this::transform)
					.collect(Collectors.toList());
		}
		
		return StreamSupport.stream(productRepository.findAll().spliterator(), false)
				.map(this::transform)
				.collect(Collectors.toList());
	}
	
	@Override
	public ProductDTO save(ProductDTO product) {
		return transform(productRepository.save(transform(product)));
	}

	@Override
	public ProductDTO findByProductId(Long productId) {
		return productRepository.findById(productId)
				.map(this::transform)
				.orElseThrow(() -> CustomException.resourceNotFoundException(productId));
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO product) {
		return productRepository.findById(productId)
				.map(oldProduct -> {
					Product productUpdated = transform(product);
					oldProduct.setName(productUpdated.getName());
					oldProduct.setManufacturer(productUpdated.getManufacturer());
					oldProduct.setSpecifications(productUpdated.getSpecifications());
					oldProduct.setUpdatedAt(LocalDateTime.now());
					return oldProduct;
				})
				.map(updatedProduct -> productRepository.save(updatedProduct))
				.map(this::transform)
				.orElseThrow(() -> CustomException.resourceNotFoundException(productId));
	}
	
	@Override
	public void deleteByProductId(Long productId) {
		productRepository.deleteById(productId);
	}
	
	private Product transform(ProductDTO productDTO) {
		return BEANS_UTILS.getTransformer()
				.withFieldTransformer(new FieldTransformer<LocalDateTime, LocalDateTime>("createdAt", x -> x))
				.withFieldTransformer(new FieldTransformer<LocalDateTime, LocalDateTime>("updatedAt", x -> x))
				.transform(productDTO, Product.class);
	}

	private ProductDTO transform(Product product) {
		return BEANS_UTILS.getTransformer()
				.withFieldTransformer(new FieldTransformer<LocalDateTime, LocalDateTime>("createdAt", x -> x))
				.withFieldTransformer(new FieldTransformer<LocalDateTime, LocalDateTime>("updatedAt", x -> x))
				.transform(product, ProductDTO.class);
	}
}
