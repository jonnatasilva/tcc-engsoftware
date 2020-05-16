package com.doggis.api.service;

import com.doggis.api.dto.SaleDTO;

public interface ISaleService {

	SaleDTO findBySaleId(Long saleId);
	SaleDTO sale(SaleDTO sale);
}
