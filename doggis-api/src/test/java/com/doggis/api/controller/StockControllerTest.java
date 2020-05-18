package com.doggis.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.doggis.api.utils.StockUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockDTO.StockProductDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.exception.CustomException;
import com.doggis.api.response.StockResponse;
import com.doggis.api.service.IStockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StockController.class)
public class StockControllerTest {

	@MockBean
	private IStockService stockService;
	
	@Autowired
	private StockController stockController;
	
	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<StockDTO> stockMapper;
	private JacksonTester<List<StockResponse>> listStockMapper;
	private JacksonTester<StockUpdateDTO> stockUpdateMapper;
	
	@Before
	public void before() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(stockController).isNotNull();
	}
	
	@Test
	public void shouldFindAllStock() throws Exception {
		List<StockResponse> stock = StockUtils.INSTANCE.createStocksResponse();
		given(stockService.findAll()).willReturn(stock);
		
		MockHttpServletResponse response = mockMvc.perform(get("/stocks"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();
		
		assertThat(response.getContentAsString()).isNotEmpty();
		
		listStockMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrProperties()
		.asList()
		.hasSize(2)
		.isEqualTo(stock);
	}
	
	@Test
	public void shouldFindStockByStockId() throws Exception {
		StockDTO shamppo = StockUtils.INSTANCE.shamppoDTO();
		given(stockService.findByStockId(shamppo.getStockId())).willReturn(shamppo);
		
		MockHttpServletResponse response = mockMvc.perform(get("/stocks/" + shamppo.getStockId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse();
		
		assertThat(response.getContentAsString()).isNotEmpty();
		
		stockMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrProperties()
		.isEqualTo(shamppo);
	}
	
	@Test
	public void shouldGetABadRequestWhenTheStockIsNotFound() throws Exception {
		given(stockService.findByStockId(3L)).willThrow(CustomException.resourceNotFoundException(3L));
			
		// when
		ResultActions actions = mockMvc.perform(get("/stocks/3"));
	
		// then
		actions
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()))
		.andExpect(jsonPath("$.description", notNullValue()));
	}
		
	@Test
	public void shouldSaveAStock() throws Exception {
		StockDTO shamppo = StockUtils.INSTANCE.shamppoDTO();
		shamppo.setStockId(null);
		given(stockService.save(ArgumentMatchers.eq(shamppo))).willReturn(StockUtils.INSTANCE.shamppoDTO());
			
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(shamppo).getJson()));
			
		//then
		actions
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", String.format("/stocks/%s", StockUtils.STOCK_SHAMPPO_ID)));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheAmountIsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(new StockDTO(null, new StockProductDTO(1L), null)).getJson()));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Stock amount is missing.")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field amount is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheAmountIsLessThanOne() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(new StockDTO(null, new StockProductDTO(1L), 0)).getJson()));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Stock amount must be greater or equal to 1.")))
		.andExpect(jsonPath("$.description", equalTo("The value 0 to the field amount is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheProductsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(new StockDTO(null, null, 1)).getJson()));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Product is missing.")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field product is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheProductIdIsMissing() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(new StockDTO(null, new StockProductDTO(), 1)).getJson()));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Stock product id is missing.")))
		.andExpect(jsonPath("$.description", equalTo("The value null to the field product.productId is not valid")));
	}
	
	@Test
	public void shouldGetABadRequestWhenTheProductIdIsLessThanOne() throws Exception {
		//when
		ResultActions actions = mockMvc.perform(post("/stocks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockMapper.write(new StockDTO(null, new StockProductDTO(0L), 1)).getJson()));
				
		//then
		actions
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", equalTo("Stock product id must be greater or equal to 1.")))
		.andExpect(jsonPath("$.description", equalTo("The value 0 to the field product.productId is not valid")));
	}
		
	@Test
	public void shouldIncreaseStockAmount() throws Exception {
		StockDTO shamppo = StockUtils.INSTANCE.shamppoDTO();
		StockUpdateDTO stockUpdate = new StockUpdateDTO(10);
		shamppo.setAmount(shamppo.getAmount() + stockUpdate.getAmount());
		given(stockService.updateStockAmount(shamppo.getStockId(), stockUpdate)).willReturn(shamppo);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(patch("/stocks/" + shamppo.getStockId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockUpdateMapper.write(stockUpdate).getJson()))
				.andReturn()
				.getResponse();
		
		stockMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrProperties()
		.isEqualTo(shamppo);
	}
	
	@Test
	public void shouldDecreaseStockAmount() throws Exception {
		StockDTO shamppo = StockUtils.INSTANCE.shamppoDTO();
		StockUpdateDTO stockUpdate = new StockUpdateDTO(-10);
		shamppo.setAmount(shamppo.getAmount() + stockUpdate.getAmount());
		given(stockService.updateStockAmount(shamppo.getStockId(), stockUpdate)).willReturn(shamppo);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(patch("/stocks/" + shamppo.getStockId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockUpdateMapper.write(stockUpdate).getJson()))
				.andReturn()
				.getResponse();
		
		stockMapper.parse(response.getContentAsString()).assertThat()
		.hasNoNullFieldsOrProperties()
		.isEqualTo(shamppo);
	}
	
	@Test
	public void shouldGetABadRequestWhenTheStockIsNotFoundOnUpdateStockAmount() throws Exception {
		StockUpdateDTO stockUpdate = new StockUpdateDTO(10);
		given(stockService.updateStockAmount(3L, stockUpdate)).willThrow(CustomException.resourceNotFoundException(3L));
		
		// when
		ResultActions actions = mockMvc.perform(patch("/stocks/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(stockUpdateMapper.write(stockUpdate).getJson()));
	
		// then
		actions
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.code", notNullValue()))
		.andExpect(jsonPath("$.timestamp", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()))
		.andExpect(jsonPath("$.description", notNullValue()));
	}
}
