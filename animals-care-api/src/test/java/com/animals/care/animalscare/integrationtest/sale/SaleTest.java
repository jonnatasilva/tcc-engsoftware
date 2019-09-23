package com.animals.care.animalscare.integrationtest.sale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.animals.care.animalscare.dto.SaleDTO;
import com.animals.care.animalscare.utils.ProductUtils;
import com.animals.care.animalscare.utils.SaleUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SaleTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private JacksonTester<SaleDTO> saleMapper;
	
	@Before
	public void before() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		JacksonTester.initFields(this, objectMapper);
	}
	
	@Test
	public void shouldSaleAProduct() throws Exception {
		SaleDTO sale = SaleUtils.INSTANCE.saleDTO();
		mockMvc.perform(post("/sales")
				.contentType(MediaType.APPLICATION_JSON)
				.content(saleMapper.write(sale).getJson()))
		.andExpect(status().isCreated())
		.andExpect(header().string("Location", "/sales/" + SaleUtils.SALE_1_ID));
		
		mockMvc.perform(get("/sales/" + SaleUtils.SALE_1_ID))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.saleProduct[0].productId", CoreMatchers.equalTo(ProductUtils.PRODUCT_SHAMPPO_ID)))
		.andExpect(jsonPath("$.saleProduct[0].amount", CoreMatchers.equalTo(SaleUtils.SALE_1_AMOUNT)));
	}
}
