package com.doggis.api.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.doggis.api.domain.Product;
import com.doggis.api.domain.Sale;
import com.doggis.api.domain.SaleProduct;
import com.doggis.api.dto.SaleDTO;
import com.doggis.api.dto.StockDTO;
import com.doggis.api.dto.StockUpdateDTO;
import com.doggis.api.service.ISaleService;
import com.doggis.api.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doggis.api.exception.CustomException;
import com.doggis.api.repository.ISaleRepository;
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
		for (SaleDTO.SaleProductDTO product : sale.getSaleProduct()) {
			stocks.put(product.getProductId(), stockService.findByProductId(product.getProductId()));
		}
		for (SaleDTO.SaleProductDTO product : sale.getSaleProduct()) {
			stockService.updateStockAmount(stocks.get(product.getProductId()).getStockId(), new StockUpdateDTO(product.getAmount() * -1));
		}
		
		return transform(repository.save(transform(sale)));
	}

	private Sale transform(SaleDTO saleDTO) {
		Function<Set<SaleDTO.SaleProductDTO>, Set<SaleProduct>> f = (x) -> {
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
		Function<Set<SaleProduct>, Set<SaleDTO.SaleProductDTO>> f = (x) -> {
			return x
					.stream()
					.map(saleProduct-> new SaleDTO.SaleProductDTO(saleProduct.getProduct().getProductId(), saleProduct.getAmount()))
					.collect(Collectors.toSet());
		};
		
		return BEANS_UTILS.getTransformer()
				.withFieldTransformer(new FieldTransformer<>("saleProduct", f))
				.transform(sale, SaleDTO.class);
	}
}
