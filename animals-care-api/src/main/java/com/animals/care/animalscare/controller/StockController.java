package com.animals.care.animalscare.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animals.care.animalscare.dto.StockDTO;
import com.animals.care.animalscare.dto.StockUpdateDTO;
import com.animals.care.animalscare.service.IStockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@CrossOrigin
@RestController
@RequestMapping("/stocks")
@Api(tags = "All apis related to stocks")
public class StockController {

	@Autowired
	private IStockService stockService;
	
	@GetMapping
	@ApiOperation(notes = "Finds all stocks", value = "findAll")
	public List<StockDTO> findAll() {
		return stockService.findAll();
	}
	
	@GetMapping("/{stockId}")
	@ApiOperation(notes = "Finds the stock by its stock id", value = "findByStockId")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "When the stock is not found by stockId")
	})
	public StockDTO findByStockId(@PathVariable Long stockId) {
		return stockService.findByStockId(stockId);
	}
	
	@PostMapping
	@ApiOperation(notes = "Saves a new stock", value = "save")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "When the stock is created successfully"
					, responseHeaders = { @ResponseHeader(name = "Location", description = "The URI to find the stock created") } ),
			@ApiResponse(code = 400, message = "When the parameters are not valid")
	})
	public ResponseEntity<Void> save(@Valid @RequestBody StockDTO stock) {
		stock = stockService.save(stock);
		
		return ResponseEntity
				.created(URI.create(String.format("/stocks/%s", stock.getStockId())))
				.build();
	}
	
	@PatchMapping("/{stockId}")
	@ApiOperation(notes = "Updates partially the stock identified by the stockId parameter", value = "updateStock")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "When the parameters are not valid"),
			@ApiResponse(code = 404, message = "When the stock to be updated is not found by stockId")
	})
	public StockDTO update(@PathVariable Long stockId,  @RequestBody StockUpdateDTO stockUpdate) {
		return stockService.updateStockAmount(stockId, stockUpdate);
	}
}
