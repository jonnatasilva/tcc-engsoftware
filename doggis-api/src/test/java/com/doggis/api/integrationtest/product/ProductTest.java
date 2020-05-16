package com.doggis.api.integrationtest.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.doggis.api.utils.ProductUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.doggis.api.dto.ManufacturerDTO;
import com.doggis.api.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonTester<ProductDTO> productMapper;
	private JacksonTester<List<ProductDTO>> listProductMapper;

	@Before
	public void before() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void shouldFindAllProducts() throws Exception {
		//Finds all products
		MockHttpServletResponse response = mockMvc.perform(get("/products"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertThat(response.getContentAsString()).isNotEmpty();
	}
	
	@Test
	public void shouldFindProductById() throws Exception {
		//Finds one product with productId equal to 1
		MockHttpServletResponse response = mockMvc.perform(get("/products/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertProduct(ProductUtils.INSTANCE.shamppoDTO(), response);
	}
	
	@Test
	public void shouldFindProducWithTheExactlyNamePassedByParameter() throws Exception {
		//Finds one product with name equal to Shamppo
		MockHttpServletResponse response = mockMvc.perform(get("/products?name=Shamppo"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertThat(response.getContentAsString()).isNotEmpty();
		
		listProductMapper.parse(response.getContentAsString()).assertThat()
		.asList()
		.hasSize(1)
		.element(0)
		.extracting(x -> ((ProductDTO) x).getName())
		.isEqualTo("Shamppo");
	}
	
	@Test
	public void shouldFindProducWithPartialNamePassedByParameter() throws Exception {
		//Finds one product with name ppo
		MockHttpServletResponse response = mockMvc.perform(get("/products?name=ppo"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertThat(response.getContentAsString()).isNotEmpty();
		
		listProductMapper.parse(response.getContentAsString()).assertThat()
		.asList()
		.hasSize(1)
		.element(0)
		.extracting(x -> ((ProductDTO) x).getName())
		.isEqualTo("Shamppo");
	}
	
	@Test
	public void shouldNotFindProducByName() throws Exception {
		//Finds one product with name ProductNotExists
		MockHttpServletResponse response = mockMvc.perform(get("/products?name=ProductNotExists"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertThat(response.getContentAsString()).isNotEmpty();
		
		listProductMapper.parse(response.getContentAsString()).assertThat()
		.asList()
		.hasSize(0);
	}
	
	@Test
	public void shouldCreateANewProduct() throws Exception {
		ProductDTO creme = ProductUtils.INSTANCE.cremeDTO();
		creme.setProductId(null);
		creme.setCreatedAt(null);
		
		//Sends the new product to be created
		MockHttpServletResponse response = createANewProduct(creme);
		
		//Gets the productId from Location header
		Long productId = getProductIdFromLocationHeader(response);
		
		//Asserts the productId
		assertThat(productId).isNotNull().isNotEqualTo(0);
		creme.setProductId(productId);
		
		//Finds the product created by its productId
		response = mockMvc.perform(get("/products/" + productId))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		//Asserts the returned content
		assertProduct(creme, response);
	}
	
	@Test
	public void shouldUpdateAProduct() throws Exception {
		ProductDTO creme = ProductUtils.INSTANCE.cremeDTO();
		creme.setProductId(null);
		creme.setCreatedAt(null);
		
		//Sends the new product to be created
		MockHttpServletResponse response = createANewProduct(creme);
		
		//Gets the productId from Location header
		Long productId = getProductIdFromLocationHeader(response);
		
		//Asserts the productId
		assertThat(productId).isNotNull().isNotEqualTo(0);
	
		ManufacturerDTO newManufacturer = new ManufacturerDTO();
		newManufacturer.setName("NewManufacturer");
		creme.setProductId(productId);
		creme.setName("ProductUpdated");
		creme.setManufacturer(newManufacturer);
		
		//Updates the product already created
		response = mockMvc.perform(put("/products/" + productId)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(productMapper.write(creme).getJson()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse();

		//Asserts the returned content
		assertThat(response.getContentAsString()).isNotEmpty();
		productMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrProperties()
		.hasFieldOrProperty("updatedAt")
		.isEqualToIgnoringGivenFields(creme, "manufacturer", "updatedAt", "createdAt")
		.extracting(ProductDTO::getManufacturer)
		.isEqualToIgnoringGivenFields(newManufacturer, "manufacturerId")
		.extracting(x -> ((ManufacturerDTO) x).getManufacturerId())
		.isNotEqualTo(0);
	}

	@Test
	public void shouldDeleteAProduct() throws Exception {
		ProductDTO creme = ProductUtils.INSTANCE.cremeDTO();
		creme.setProductId(null);
		creme.setCreatedAt(null);
		
		//Sends the new product to be created
		MockHttpServletResponse response = createANewProduct(creme);
		
		//Gets the productId from Location header
		Long productId = getProductIdFromLocationHeader(response);
		
		//Deletes de product created
		mockMvc.perform(delete("/products/" + productId))
				.andDo(print())
				.andExpect(status().isOk());
		
		//Finds the product deleted by its productId
		mockMvc.perform(get("/products/" + productId))
				.andDo(print())
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void shouldGetAnErrorMessageOnFindAProductThatTheProductIsNotFound() throws Exception {
		mockMvc.perform(get("/products/8150"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("code", equalTo(1)))
				.andExpect(jsonPath("timestamp", notNullValue()))
				.andExpect(jsonPath("message", equalTo("Resource not found.")))
				.andExpect(jsonPath("description", equalTo("Resource with id 8150 is not found.")));
	}
	
	@Test
	public void shouldGetAnErrorMessageOnUpdateAProductThatTheProductIsNotFound() throws Exception {
		mockMvc.perform(put("/products/8150")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(productMapper.write(ProductUtils.INSTANCE.shamppoDTO()).getJson()))
		.andDo(print())
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("code", equalTo(1)))
		.andExpect(jsonPath("timestamp", notNullValue()))
		.andExpect(jsonPath("message", equalTo("Resource not found.")))
		.andExpect(jsonPath("description", equalTo("Resource with id 8150 is not found.")));
	}

	private void assertProduct(ProductDTO expectedProduct, MockHttpServletResponse response) throws UnsupportedEncodingException, IOException {
		assertThat(response.getContentAsString()).isNotEmpty();
		productMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrPropertiesExcept("updatedAt")
		.hasFieldOrProperty("createdAt")
		.isEqualToIgnoringGivenFields(expectedProduct, "createdAt");
	}
	
	private Long getProductIdFromLocationHeader(MockHttpServletResponse response) {
		return Long.valueOf(response.getHeader(HttpHeaders.LOCATION)
				.split("/products/")[1]);
	}
	
	private MockHttpServletResponse createANewProduct(ProductDTO creme) throws Exception, IOException {
		return mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(productMapper.write(creme).getJson()))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, startsWith("/products/")))
				.andReturn().getResponse();
	}
}