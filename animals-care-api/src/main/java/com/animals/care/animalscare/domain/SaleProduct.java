package com.animals.care.animalscare.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "sale_product")
public class SaleProduct implements Serializable {

	private static final long serialVersionUID = 6583395357224065076L;

	@Id
	@GeneratedValue
	private Long id;

	private Integer amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public SaleProduct(Integer amount, Product product) {
		this.amount = amount;
		this.product = product;
	}
	
}
