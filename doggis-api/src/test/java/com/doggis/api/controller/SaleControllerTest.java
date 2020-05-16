package com.doggis.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.doggis.api.utils.SaleUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.doggis.api.dto.SaleDTO;
import com.doggis.api.service.ISaleService;
import com.doggis.api.service.IStockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SaleController.class)
public class SaleControllerTest {

	@MockBean
	private ISaleService saleService;
	
	@MockBean
	private IStockService stockService;
	
	@Autowired
	private SaleController saleController;
	
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonTester<SaleDTO> saleMapper;
	
	@Before
	public void before() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void contextLoads() {
		assertThat(saleController).isNotNull();
	}
	
	@Test
	public void shouldSaleAProduct() throws Exception {
		SaleDTO saleDTO = SaleUtils.INSTANCE.saleDTO();
		saleDTO.setSaleId(null);
		given(saleService.sale(ArgumentMatchers.eq(saleDTO))).willReturn(SaleUtils.INSTANCE.saleDTO());
		
		mockMvc.perform(post("/sales")
				.contentType(MediaType.APPLICATION_JSON)
				.content(saleMapper.write(saleDTO).getJson()))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", "/sales/" + SaleUtils.INSTANCE.saleDTO().getSaleId()));
	}
}
