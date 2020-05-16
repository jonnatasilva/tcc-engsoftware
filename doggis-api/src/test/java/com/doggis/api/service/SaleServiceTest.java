package com.doggis.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import com.doggis.api.utils.StockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.doggis.api.dto.SaleDTO;
import com.doggis.api.dto.SaleDTO.SaleProductDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.repository.ISaleRepository;
import com.doggis.api.service.impl.SaleServiceImpl;
import com.doggis.api.utils.SaleUtils;

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
