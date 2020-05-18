package com.doggis.api.service;

import java.util.List;

import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.response.StockResponse;

public interface IStockService {

	List<StockResponse> findAll();

	StockDTO findByStockId(Long stockId);
	
	StockDTO findByProductId(Long productId);

	StockDTO save(StockDTO eq);

	StockDTO updateStockAmount(Long stockId, StockUpdateDTO stockUpdate);

}
