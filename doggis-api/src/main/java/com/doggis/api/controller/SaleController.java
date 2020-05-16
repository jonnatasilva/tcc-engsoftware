package com.doggis.api.controller;

import java.net.URI;

import com.doggis.api.dto.SaleDTO;
import com.doggis.api.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/sales")
@Api(tags = "All apis related to sales")
public class SaleController {

	@Autowired
	private ISaleService saleService;

	@GetMapping("/{saleId}")
	@ApiOperation(notes = "Finds the sale by sale id", value = "findBySaleId")
	public SaleDTO findBySaleId(@PathVariable Long saleId) {
		return saleService.findBySaleId(saleId);
	}
	
	@PostMapping
	public ResponseEntity<Void> save(@RequestBody SaleDTO sale) {
		SaleDTO saleDTO = saleService.sale(sale);
		return ResponseEntity
				.created(URI.create(String.format("/sales/%s", saleDTO.getSaleId())))
				.build();
	}
}
