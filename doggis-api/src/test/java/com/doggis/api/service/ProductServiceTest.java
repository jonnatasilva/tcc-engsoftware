package com.doggis.api.service;

import com.doggis.api.domain.QProduct;
import com.doggis.api.domain.Product;
import com.doggis.api.dto.ProductDTO;
import com.doggis.api.dto.ProductFilter;
import com.doggis.api.exception.CustomException;
import com.doggis.api.exception.CustomException.ErrorCode;
import com.doggis.api.repository.IProductRepository;
import com.doggis.api.service.impl.ProductServiceImpl;
import com.doggis.api.utils.CustomProductMatcher;
import com.doggis.api.utils.ProductUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@Mock
	private IProductRepository productRepository;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(productService).isNotNull();
	}
	
	@Test
	public void shouldFindAllProducts() {
		//given
		Iterable<Product> products = ProductUtils.INSTANCE.products();
		given(productRepository.findAll()).willReturn(products);
		
		//when
		List<ProductDTO> productsDTO = productService.findAll(null);
		
		//then
		assertThat(productsDTO).isNotEmpty();
		assertThat(productsDTO).containsAll(ProductUtils.INSTANCE.productsDTO());
	}
	
	@Test
	public void shouldFindAllProductsFilteringByName() {
		//given
		Iterable<Product> products = Arrays.asList(ProductUtils.INSTANCE.shamppo());
		BooleanExpression predicate = QProduct.product.name.containsIgnoreCase(ProductUtils.PRODUCT_SHAMPPO_NAME);
		given(productRepository.findAll(eq(predicate))).willReturn(products);
				
		//when
		ProductFilter productFilter = new ProductFilter(ProductUtils.PRODUCT_SHAMPPO_NAME);
		List<ProductDTO> productsDTO = productService.findAll(productFilter);
				
		//then
		assertThat(productsDTO).isNotEmpty();
		assertThat(productsDTO).contains(ProductUtils.INSTANCE.shamppoDTO());
	}
	
	@Test
	public void shouldNotFindAllUsingPredicateWhenProductFilterNameIsEmpty() {
		//given
		Iterable<Product> products = ProductUtils.INSTANCE.products();
		given(productRepository.findAll()).willReturn(products);
				
		//when
		ProductFilter productFilter = new ProductFilter("");
		List<ProductDTO> productsDTO = productService.findAll(productFilter);
				
		//then
		assertThat(productsDTO).isNotEmpty();
		assertThat(productsDTO).containsAll(ProductUtils.INSTANCE.productsDTO());
	}
	
	@Test
	public void shouldFindNoProducts() {
		//given
		given(productRepository.findAll()).willReturn(Collections.emptyList());
				
		//when
		List<ProductDTO> productsDTO = productService.findAll(null);
				
		//then
		assertThat(productsDTO).isEmpty();
	}
	
	@Test
	public void shouldFindNoProductsFilteringByName() {
		//when
		ProductFilter productFilter = new ProductFilter("NaoExiste");
		List<ProductDTO> productsDTO = productService.findAll(productFilter);
				
		//then
		assertThat(productsDTO).isEmpty();
	}
	
	@Test
	public void shouldFindProductByProductId() {
		//given
		given(productRepository.findById(eq(ProductUtils.PRODUCT_SHAMPPO_ID.longValue()))).willReturn(Optional.of(ProductUtils.INSTANCE.shamppo()));
						
		//when
		ProductDTO productDTO = productService.findByProductId(ProductUtils.PRODUCT_SHAMPPO_ID.longValue());
						
		//then
		assertThat(productDTO).isNotNull();
		assertThat(productDTO).isEqualTo(ProductUtils.INSTANCE.shamppoDTO());
	}
	
	@Test
	public void shouldThrowsAnExceptionWhenTheProductIsNotFoundByProductId() {
		//given
		given(productRepository.findById(eq(3L))).willReturn(Optional.empty());
						
		//then
		thrown.expect(CustomException.class);
		thrown.expect(hasProperty("code", is(ErrorCode.RESOURCE_NOT_FOUND.getCode())));

		//when
		productService.findByProductId(3L);
	}
	
	@Test
	public void shouldSaveAProduct() {
		//given
		Product product = ProductUtils.INSTANCE.shamppo();
		product.setProductId(0L);
		product.setCreatedAt(null);
		product.getManufacturer().setManufacturerId(0L);
		given(productRepository.save(eq(product))).willReturn(ProductUtils.INSTANCE.shamppo());
		
		//when
		ProductDTO productDTO = ProductUtils.INSTANCE.shamppoDTO();
		productDTO.setProductId(0L);
		productDTO.setCreatedAt(null);
		productDTO.getManufacturer().setManufacturerId(0L);
		productDTO = productService.save(productDTO);
		
		//then
		assertThat(productDTO).isNotNull();
		assertThat(productDTO).isEqualTo(ProductUtils.INSTANCE.shamppoDTO());
	}
	
	@Test
	public void shouldUpdateAProduct() {
		//given
		given(productRepository.findById(ProductUtils.PRODUCT_SHAMPPO_ID.longValue())).willReturn(Optional.of(ProductUtils.INSTANCE.shamppo()));
		
		Product product = ProductUtils.INSTANCE.shamppo();
		product.setName("Teste");
		product.setUpdatedAt(LocalDateTime.now());
		product.setManufacturer(ProductUtils.INSTANCE.sanol());
		product.setSpecifications("Specifications");
		given(productRepository.save(argThat(new CustomProductMatcher(product)))).willReturn(product);
		
		//when
		ProductDTO productDTO = ProductUtils.INSTANCE.shamppoDTO();
	    productDTO.setName("Teste");
	    productDTO.setSpecifications("Specifications");
	    productDTO.setManufacturer(ProductUtils.INSTANCE.sanolDTO());
	    ProductDTO updatedProductDTO = productService.updateProduct(ProductUtils.PRODUCT_SHAMPPO_ID.longValue(), productDTO);
				
		//then
		assertThat(updatedProductDTO).isNotNull();
		assertThat(updatedProductDTO.getUpdatedAt()).isNotNull();
		productDTO.setUpdatedAt(updatedProductDTO.getUpdatedAt());
	    assertThat(updatedProductDTO).isEqualTo(productDTO);
	}
	
	@Test
	public void shouldThrowsAnExceptionOnUpdateAProductWhenTheProductIsNotFound() {
		//given
		given(productRepository.findById(3L)).willReturn(Optional.empty());
		
		//then
		thrown.expect(CustomException.class);
		thrown.expect(hasProperty("code", is(ErrorCode.RESOURCE_NOT_FOUND.getCode())));
				
		//when
		ProductDTO productDTO = ProductUtils.INSTANCE.shamppoDTO();
	    productDTO.setName("Teste");
	    productService.updateProduct(3L, productDTO);
	}
	
	@Test
	public void shouldDeleteAProduct() {
		//when 
		productService.deleteByProductId(ProductUtils.PRODUCT_SHAMPPO_ID.longValue());
		
		//then
		verify(productRepository, times(1)).deleteById(ProductUtils.PRODUCT_SHAMPPO_ID.longValue());
	}
}
