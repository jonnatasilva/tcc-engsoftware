package com.animals.care.animalscare.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.animals.care.animalscare.domain.Stock;
import com.animals.care.animalscare.dto.StockDTO;
import com.animals.care.animalscare.dto.StockUpdateDTO;
import com.animals.care.animalscare.exception.CustomException;
import com.animals.care.animalscare.repository.IStockRepository;
import com.animals.care.animalscare.service.IStockService;
import com.hotels.beans.BeanUtils;

@Service
public class StockServiceImpl implements IStockService {

	private static final BeanUtils BEANS_UTILS = new BeanUtils();
	
	@Autowired
	private IStockRepository stockRepository;
	
	@Override
	public List<StockDTO> findAll() {
		return stockRepository.findAll()
				.stream()
				.map(this::transform)
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
}
