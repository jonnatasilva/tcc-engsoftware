package com.doggis.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doggis.api.domain.Stock;
import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.exception.CustomException;
import com.doggis.api.repository.IStockRepository;
import com.doggis.api.response.StockResponse;
import com.doggis.api.service.IStockService;
import com.hotels.beans.BeanUtils;

@Service
public class StockServiceImpl implements IStockService {

	private static final BeanUtils BEANS_UTILS = new BeanUtils();
	
	@Autowired
	private IStockRepository stockRepository;

	@Override
	public List<StockResponse> findAll() {
		return stockRepository.findAll()
				.stream()
				.map(this::transformToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public StockDTO findByStockId(Long stockId) {
		return stockRepository.findById(stockId)
				.map(this::transform)
				.orElseThrow(() -> CustomException.resourceNotFoundException(stockId));
	}
	
	@Override
	public StockDTO findByProductId(Long productId) {
		return stockRepository.findByProductProductId(productId)
				.map(this::transform)
				.orElseThrow(() -> CustomException.resourceNotFoundException(productId));
	}

	@Override
	public StockDTO save(StockDTO stock) {
		stockRepository
		.findByProductProductId(stock.getProduct().getProductId())
		.ifPresent(it -> {
			stock.setStockId(it.getStockId());
			stock.setAmount(stock.getAmount() + it.getAmount());
		});
		
		return this.transform(stockRepository.save(this.transform(stock)));
	}

	@Override
	public StockDTO updateStockAmount(Long stockId, StockUpdateDTO stockUpdate) {
		Stock stock = this.transform(findByStockId(stockId));
		
		if (stock.getAmount() + stockUpdate.getAmount() < 0) {
			throw new IllegalStateException("The stock amount can not be updated to " + (stock.getAmount() + stockUpdate.getAmount())
			+ " the stock amount must be greater or equal to ZERO after being updated.");
		}
		stock.setAmount(stock.getAmount() + stockUpdate.getAmount());
		
		return this.transform(stockRepository.save(stock));
	}

	private Stock transform(StockDTO stockDTO) {
		return BEANS_UTILS.getTransformer()
				.skipTransformationForField("product.name")
				.skipTransformationForField("product.price")
				.skipTransformationForField("product.specifications")
				.skipTransformationForField("product.manufacturer")
				.skipTransformationForField("product.createdAt")
				.skipTransformationForField("product.updatedAt")
				.transform(stockDTO, Stock.class);
	}

	private StockDTO transform(Stock stock) {
		return BEANS_UTILS.getTransformer()
				.skipTransformationForField("name")
				.transform(stock, StockDTO.class);
	}
	
	private StockResponse transformToResponse(Stock stock) {
		return new StockResponse(stock.getStockId(), stock.getProduct(), stock.getAmount());
	}

}
