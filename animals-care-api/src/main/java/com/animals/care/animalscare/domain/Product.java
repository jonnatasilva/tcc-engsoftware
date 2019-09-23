package com.animals.care.animalscare.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product implements Serializable {
	
	private static final long serialVersionUID = 3806427088285850134L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	private String name;
	
	private String specifications;
	
	@ManyToOne(cascade = CascadeType.MERGE, targetEntity = Manufacturer.class)
	@JoinColumn(name = "manufacturer_id", referencedColumnName = "manufacturer_id")
	private Manufacturer manufacturer;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP AS CURRENT_TIMESTAMP", updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	public Product(Long productId) {
		this.productId = productId;
	}
}
