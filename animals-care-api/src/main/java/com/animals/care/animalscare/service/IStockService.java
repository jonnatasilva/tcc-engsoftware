package com.animals.care.animalscare.service;

import java.util.List;

import com.animals.care.animalscare.dto.StockDTO;
import com.animals.care.animalscare.dto.StockUpdateDTO;

public interface IStockService {

	List<StockDTO> findAll();

	StockDTO findByStockId(Long stockId);
	
	StockDTO findByProductId(Long productId);

	StockDTO save(StockDTO eq);

	StockDTO updateStockAmount(Long stockId, StockUpdateDTO stockUpdate);

}
