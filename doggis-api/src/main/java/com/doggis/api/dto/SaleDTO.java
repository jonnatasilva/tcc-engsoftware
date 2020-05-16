package com.doggis.api.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SaleDTO {

	private Long saleId;
	private LocalDateTime saleDate;
	private Set<SaleProductDTO> saleProduct;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class SaleProductDTO {
		private Long productId;
		private Integer amount;
	}
}
