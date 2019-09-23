package com.animals.care.animalscare.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.animals.care.animalscare.dto.SaleDTO;
import com.animals.care.animalscare.dto.SaleDTO.SaleProductDTO;
import com.animals.care.animalscare.dto.StockUpdateDTO;
import com.animals.care.animalscare.repository.ISaleRepository;
import com.animals.care.animalscare.service.impl.SaleServiceImpl;
import com.animals.care.animalscare.utils.SaleUtils;
import com.animals.care.animalscare.utils.StockUtils;

@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest {

	@Mock
	private ISaleRepository saleRepository;
	
	@Mock
	private IStockService stockService;
	
	@InjectMocks
	private SaleServiceImpl saleService;
	
	@Test
	public void contextLoads() {
		assertThat(saleRepository).isNotNull();
	}
	
	@Test
	public void shouldSaleAProduct() {
		given(stockService.findByProductId(1L)).willReturn(StockUtils.INSTANCE.cremeDTO());
		given(saleRepository.save(eq(SaleUtils.INSTANCE.newSale()))).willReturn(SaleUtils.INSTANCE.saleCreated());
		
		SaleDTO saleDTOCreated = saleService.sale(SaleUtils.INSTANCE.saleDTO());
		
		verify(stockService, times(1)).updateStockAmount(1L, new StockUpdateDTO(-2));
		Set<SaleProductDTO> saleProductExpected = new HashSet<>();
		saleProductExpected.add(new SaleProductDTO(1L, 2));
		assertThat(saleDTOCreated).isEqualTo(new SaleDTO(1L, SaleUtils.saleDate, saleProductExpected));
	}
}
