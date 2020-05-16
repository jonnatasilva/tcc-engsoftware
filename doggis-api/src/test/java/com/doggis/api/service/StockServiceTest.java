package com.doggis.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import com.doggis.api.utils.StockUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.doggis.api.domain.Product;
import com.doggis.api.domain.Stock;
import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.exception.CustomException;
import com.doggis.api.exception.CustomException.ErrorCode;
import com.doggis.api.repository.IStockRepository;
import com.doggis.api.service.impl.StockServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

	@Mock
	private IStockRepository stockRepository;

	@InjectMocks
	private StockServiceImpl stockService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void contextLoads() {
		assertThat(stockService).isNotNull();
	}

	@Test
	public void shouldFindAllStocks() {
		List<Stock> expectedStocks = StockUtils.INSTANCE.createStocks();
		given(stockRepository.findAll()).willReturn(expectedStocks);

		List<StockDTO> stocks = stockService.findAll();

		assertThat(stocks).isEqualTo(StockUtils.INSTANCE.createStocksDTO());
	}

	@Test
	public void shouldFindStockByStockId() {
		given(stockRepository.findById(StockUtils.STOCK_SHAMPPO_ID)).willReturn(Optional.of(StockUtils.INSTANCE.shamppo()));

		StockDTO stock = stockService.findByStockId(StockUtils.STOCK_SHAMPPO_ID);

		assertThat(stock).isEqualTo(StockUtils.INSTANCE.shamppoDTO());
	}
	
	@Test
	public void shouldThrowsAnExceptionWhenTheStockIsNotFoundByStockId() {
		given(stockRepository.findById(3L)).willReturn(Optional.empty());
		
		//then
		thrown.expect(CustomException.class);
		thrown.expect(hasProperty("code", is(ErrorCode.RESOURCE_NOT_FOUND.getCode())));
				
		stockService.findByStockId(3L);
	}
	
	@Test
	public void shouldSaveANewStock() {
		Stock stock = StockUtils.INSTANCE.shamppo();
		stock.setStockId(0L);
		stock.setProduct(new Product(stock.getProduct().getProductId()));
		given(stockRepository.save(ArgumentMatchers.eq(stock))).willReturn(StockUtils.INSTANCE.shamppo());
		
		StockDTO stockDTO = StockUtils.INSTANCE.shamppoDTO();
		stockDTO.setStockId(null);
		stockDTO = stockService.save(stockDTO);
		
		assertThat(stockDTO).isEqualTo(StockUtils.INSTANCE.shamppoDTO());
	}
	
	@Test
	public void shouldIncreaseTheStockAmount() {
		Stock stock = StockUtils.INSTANCE.shamppo();
		given(stockRepository.findById(StockUtils.STOCK_SHAMPPO_ID)).willReturn(Optional.of(stock));

		int increaseAmount = 10;
		
		Stock stockSave = StockUtils.INSTANCE.shamppo();
		stockSave.setAmount(stockSave.getAmount() + increaseAmount);
		stockSave.setProduct(new Product(stockSave.getProduct().getProductId()));
		given(stockRepository.save(ArgumentMatchers.eq(stockSave))).willReturn(stockSave);
		
		StockDTO stockUpdated = stockService.updateStockAmount(StockUtils.STOCK_SHAMPPO_ID, new StockUpdateDTO(increaseAmount));
		
		assertThat(stockUpdated.getAmount()).isEqualTo(20);
	}
	
	@Test
	public void shouldDecreaseTheStockAmount() {
		Stock stock = StockUtils.INSTANCE.shamppo();
		given(stockRepository.findById(StockUtils.STOCK_SHAMPPO_ID)).willReturn(Optional.of(stock));

		int increaseAmount = -10;
		
		Stock stockSave = StockUtils.INSTANCE.shamppo();
		stockSave.setAmount(stockSave.getAmount() + increaseAmount);
		stockSave.setProduct(new Product(stockSave.getProduct().getProductId()));
		given(stockRepository.save(ArgumentMatchers.eq(stockSave))).willReturn(stockSave);
		
		StockDTO stockUpdated = stockService.updateStockAmount(StockUtils.STOCK_SHAMPPO_ID, new StockUpdateDTO(increaseAmount));
		
		assertThat(stockUpdated.getAmount()).isEqualTo(0);
	}
	
	@Test
	public void shouldThrowsAnExceptionWhenTheStockAmountWouldBeUpdatedToLessThanZero() {
		Stock stock = StockUtils.INSTANCE.shamppo();
		given(stockRepository.findById(StockUtils.STOCK_SHAMPPO_ID)).willReturn(Optional.of(stock));

				
		int increaseAmount = -11;

		//then
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("The stock amount can not be updated to " + (stock.getAmount() + increaseAmount) + " the stock amount must be greater or equal to ZERO after being updated.");
		
		stockService.updateStockAmount(StockUtils.STOCK_SHAMPPO_ID, new StockUpdateDTO(increaseAmount));
	}
}
