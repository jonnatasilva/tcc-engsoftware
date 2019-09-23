package com.animals.care.animalscare.service;

import com.animals.care.animalscare.dto.SaleDTO;

public interface ISaleService {

	SaleDTO findBySaleId(Long saleId);
	SaleDTO sale(SaleDTO sale);
}
