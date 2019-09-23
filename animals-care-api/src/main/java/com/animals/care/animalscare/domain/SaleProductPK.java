package com.animals.care.animalscare.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SaleProductPK implements Serializable {

	private static final long serialVersionUID = 3865417932883056978L;

	@Column(name = "sale_id")
	private Long saleId;
	
	@Column(name = "product_id")
	private Long productId;
}
