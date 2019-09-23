package com.animals.care.animalscare.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animals.care.animalscare.dto.ProductDTO;
import com.animals.care.animalscare.dto.ProductFilter;
import com.animals.care.animalscare.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * @author jonnatas
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
@Api(tags = "All apis related to products")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	@ApiOperation(notes = "Finds all products", value = "findAll")
	public List<ProductDTO> findAll(ProductFilter filter, HttpServletRequest request, HttpServletResponse response) {
		return productService.findAll(filter);
	}
	
	@PostMapping
	@ApiOperation(notes = "Saves a new product", value = "save")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "When the product is created successfully"
					, responseHeaders = { @ResponseHeader(name = "Location", description = "The URI to find the product created") } ),
			@ApiResponse(code = 400, message = "When the parameters are not valid")
	})
	public ResponseEntity<Void> save(@Valid @RequestBody ProductDTO product) {
		product = productService.save(product);
		return ResponseEntity
				.created(URI.create(String.format("/products/%s", product.getProductId())))
				.build();
	}
	
	@GetMapping("/{productId}")
	@ApiOperation(notes = "Finds the product by its product id", value = "findByProductId")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "When the product is not found by productId")
	})
	public ProductDTO findByProductId(@PathVariable Long productId) {
		return productService.findByProductId(productId);
	}
	
	@PutMapping("/{productId}")
	@ApiOperation(notes = "Updates the whole product identified by the productId parameter", value = "updateProduct")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "When the parameters are not valid"),
			@ApiResponse(code = 404, message = "When the product to be updated is not found by productId")
	})
	public ProductDTO updateProduct(@PathVariable Long productId, @RequestBody ProductDTO product) {
		return productService.updateProduct(productId, product);
	}
	
	@DeleteMapping("/{productId}")
	@ApiOperation(notes = "Deletes the product by its product id", value = "deleteByProductId")
	public void deleteProduct(@PathVariable Long productId) {
		productService.deleteByProductId(productId);
	}
}
