package com.doggis.api.repository;

import java.util.Optional;

import com.doggis.api.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockRepository extends JpaRepository<Stock, Long> {

	public Optional<Stock> findByProductProductId(Long productId);
}
