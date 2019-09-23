package com.animals.care.animalscare.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.animals.care.animalscare.domain.Product;
import com.animals.care.animalscare.domain.Sale;
import com.animals.care.animalscare.domain.SaleProduct;
import com.animals.care.animalscare.dto.SaleDTO;
import com.animals.care.animalscare.dto.SaleDTO.SaleProductDTO;
import com.animals.care.animalscare.dto.StockDTO;
import com.animals.care.animalscare.dto.StockUpdateDTO;
import com.animals.care.animalscare.exception.CustomException;
import com.animals.care.animalscare.repository.ISaleRepository;
import com.animals.care.animalscare.service.ISaleService;
import com.animals.care.animalscare.service.IStockService;
import com.hotels.beans.BeanUtils;
import com.hotels.beans.model.FieldTransformer;

@Service
public class SaleServiceImpl implements ISaleService {
	
	private static final BeanUtils BEANS_UTILS = new BeanUtils();

	@Autowired
	private ISaleRepository repository;
	
	@Autowired
	private IStockService stockService;
	
	@Override
	public SaleDTO findBySaleId(Long saleId) {
		return repository.findById(saleId)
				.map(this::transform)
				.orElseThrow(() -> CustomException.resourceNotFoundException(saleId));
	}
	
	@Override
	@Transactional
	public SaleDTO sale(SaleDTO sale) {
		Map<Long, StockDTO> stocks = new HashMap<>();
		for (SaleProductDTO product : sale.getSaleProduct()) {
			stocks.put(product.getProductId(), stockService.findByProductId(product.getProductId()));
		}
		for (SaleProductDTO product : sale.getSaleProduct()) {			
			stockService.updateStockAmount(stocks.get(product.getProductId()).getStockId(), new StockUpdateDTO(product.getAmount() * -1));
		}
		
		return transform(repository.save(transform(sale)));
	}

	private Sale transform(SaleDTO saleDTO) {
		Function<Set<SaleProductDTO>, Set<SaleProduct>> f = (x) -> {
			return x
					.stream()
					.map(saleProduct-> new SaleProduct(saleProduct.getAmount(), new Product(saleProduct.getProductId())))
					.collect(Collectors.toSet());
		};
		
		return BEANS_UTILS.getTransformer()
				.withFieldTransformer(new FieldTransformer<>("saleProduct", f))
				.transform(saleDTO, Sale.class);
	}

	private SaleDTO transform(Sale sale) {
		Function<Set<SaleProduct>, Set<SaleProductDTO>> f = (x) -> {
			return x
					.stream()
					.map(saleProduct-> new SaleProductDTO(saleProduct.getProduct().getProductId(), saleProduct.getAmount()))
					.collect(Collectors.toSet());
		};
		
		return BEANS_UTILS.getTransformer()
				.withFieldTransformer(new FieldTransformer<>("saleProduct", f))
				.transform(sale, SaleDTO.class);
	}
}
