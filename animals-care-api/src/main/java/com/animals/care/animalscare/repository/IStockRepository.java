package com.animals.care.animalscare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.animals.care.animalscare.domain.Stock;

public interface IStockRepository extends JpaRepository<Stock, Long> {

	public Optional<Stock> findByProductProductId(Long productId);
}
