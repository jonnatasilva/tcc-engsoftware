package com.doggis.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.doggis.api.utils.ProductUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.doggis.api.dto.ManufacturerDTO;
import com.doggis.api.dto.ProductDTO;
import com.doggis.api.dto.ProductFilter;
import com.doggis.api.exception.CustomException;
import com.doggis.api.service.IBuildHeaderService;
import com.doggis.api.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	
	@MockBean
	private IBuildHeaderService buildHeaderService;

	@MockBean
	private IProductService productService;

	@Autowired
	private ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private Page<ProductDTO> page;
	
	private JacksonTester<ProductDTO> productMapper;
	private JacksonTester<List<ProductDTO>> listProductsMapper;

	@Before
	public void before() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(productController).isNotNull();
	}

	@Test
	public void shouldReturnNoProducts() throws Exception {
		// given
		given(productService.findAll(any(ProductFilter.class))).willReturn(Collections.emptyList());

		// when
		mockMvc.perform(get("/products"))

		// then
	   .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldReturnAListOfProducts() throws Exception {
		// given
		List<ProductDTO> products = ProductUtils.INSTANCE.productsDTO();
		given(productService.findAll(any(ProductFilter.class))).willReturn(products);

		// when
		ResultActions actions = mockMvc.perform(get("/products"));

		// then
		MockHttpServletResponse response = actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andReturn().getResponse();
		
		assertThat(response.getContentAsString()).isEqualTo(listProductsMapper.write(products).getJson());
	}
	
	@Test
	public void shouldFilterProductsByName() throws Exception {
		// given
		ProductFilter filter = new ProductFilter(ProductUtils.PRODUCT_SHAMPPO_NAME);
		ProductDTO shamppo = ProductUtils.INSTANCE.shamppoDTO();
		given(productService.findAll(eq(filter))).willReturn(Arrays.asList(shamppo));

		// when
		ResultActions actions = mockMvc.perform(get(String.format("/products?name=%s", ProductUtils.PRODUCT_SHAMPPO_NAME)));

		// then
		MockHttpServletResponse response = actions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andReturn().getResponse();
		
		assertThat(response.getContentAsString()).isEqualTo(listProductsMapper.write(Arrays.asList(shamppo)).getJson());
	}
	
	@Test
	public void shouldFindProductById() throws Exception {
		// given
		ProductDTO creme = ProductUtils.INSTANCE.cremeDTO();
		given(productService.findByProductId(ProductUtils.PRODUCT_CREME_ID.longValue())).willReturn(creme);

		// when
		ResultActions actions = mockMvc.perform(get(String.format("/products/%s", ProductUtils.PRODUCT_CREME_ID)));

		// then
		MockHttpServletResponse response = actions
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		assertThat(response.getContentAsString()).isEqualTo(productMapper.write(creme).getJson());
	}
	
	@Test
	public void shouldGetABadRequestWhenTheProductIsNotFound() throws Exception {
		// given
		given(productService.findByProductId(3L)).willThrow(CustomException.resourceNotFoundException(3L));

		// when
		ResultActions actions = mockMvc.perform(get("/products/3"));

		// then
		actions
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()))
		.andExpect(jsonPath("$.description", notNullValue()));
	}
	
	@Test
	public void shouldSaveAProduct() throws Exception {
		//given
		ManufacturerDTO manufacturer = new ManufacturerDTO(null, ProductUtils.MANUFACTURER_LOREAL_NAME);
		ProductDTO product = ProductDTO.builder()
				.name(ProductUtils.PRODUCT_SHAMPPO_NAME)
				.manufacturer(manufacturer)
				.price(BigDecimal.TEN)
				.build();
		
		given(productService.save(eq(product))).willReturn((ProductUtils.INSTANCE.shamppoDTO()));
		
		//when
		ResultActions actions = mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productMapper.write(product).getJson()));
		
		//then
		actions
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", String.format("/products/%s", ProductUtils.PRODUCT_SHAMPPO_ID)));
		
	}
	
	@Test
	public void shouldGetABadRequestWhenTheNameIsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Shamppo\", \"price\": 10}"));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Product manufacturer is missing")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field manufacturer is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheManufacturerIsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"price\": 10, \"manufacturer\":{\"name\":\"L'Oréal\"}}"));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Product name is missing")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field name is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheManufacturerNameIsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"price\": 10, \"name\":\"Shamppo\", \"manufacturer\":{}}"));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Manufacturer name is missing")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field manufacturer.name is not valid")));
	}
	
	@Test
	public void shouldUpdateAProduct() throws Exception {
		// given
		ProductDTO updatedProduct = ProductUtils.INSTANCE.shamppoDTO();
		updatedProduct.setName("ShamppoUpdated");
		given(productService.updateProduct(eq(ProductUtils.PRODUCT_SHAMPPO_ID.longValue()), eq(updatedProduct))).willReturn(updatedProduct);
				
		//when
		ResultActions actions = mockMvc.perform(put(String.format("/products/%s", ProductUtils.PRODUCT_SHAMPPO_ID))
				.contentType(MediaType.APPLICATION_JSON)
				.content(productMapper.write(updatedProduct).getJson()));
		
		// then
		MockHttpServletResponse response = actions.andExpect(status().isOk())
				.andReturn()
				.getResponse();
		assertThat(response.getContentAsString()).isEqualTo(productMapper.write(updatedProduct).getJson());
	}
	
	@Test
	public void shouldGetABadRequestWhenTheProductIdToBeUpdatedIsNotFound() throws Exception {
		// given
		ProductDTO updatedProduct = ProductUtils.INSTANCE.shamppoDTO();
		updatedProduct.setName("ShamppoUpdated");
		given(productService.updateProduct(eq(3l), eq(updatedProduct))).willThrow(CustomException.resourceNotFoundException(3L));

		// when
		ResultActions actions = mockMvc.perform(put("/products/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productMapper.write(updatedProduct).getJson()));

		// then
		actions
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()))
		.andExpect(jsonPath("$.description", notNullValue()));
	}
}
