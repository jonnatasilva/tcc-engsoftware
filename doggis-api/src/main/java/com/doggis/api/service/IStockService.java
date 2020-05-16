package com.doggis.api.service;

import java.util.List;

import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockUpdateDTO;

public interface IStockService {

	List<StockDTO> findAll();

	StockDTO findByStockId(Long stockId);
	
	StockDTO findByProductId(Long productId);

	StockDTO save(StockDTO eq);

	StockDTO updateStockAmount(Long stockId, StockUpdateDTO stockUpdate);

}
