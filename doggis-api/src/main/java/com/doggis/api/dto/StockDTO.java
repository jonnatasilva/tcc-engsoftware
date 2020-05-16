package com.doggis.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

	private Long stockId;
	
	@NotNull(message = "Product is missing.")
	@Valid
	private StockProductDTO product;
	
	@NotNull(message = "Stock amount is missing.")
	@Min(value = 1, message = "Stock amount must be greater or equal to 1.")
	private Integer amount;
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StockProductDTO {
		@NotNull(message = "Stock product id is missing.")
		@Min(value = 1, message = "Stock product id must be greater or equal to 1.")
		private Long productId;
	}
}
