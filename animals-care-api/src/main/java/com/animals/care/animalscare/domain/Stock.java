package com.animals.care.animalscare.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Stock implements Serializable {

	private static final long serialVersionUID = -7973975233213033386L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stockId;
	
	@OneToOne(targetEntity = Product.class)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;
	
	private Integer amount;
}
