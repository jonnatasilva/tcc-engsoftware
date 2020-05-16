package com.doggis.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
public class Manufacturer implements Serializable {
	
	private static final long serialVersionUID = 5060552492602140439L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manufacturer_id")
	private Long manufacturerId;
	
	@NotEmpty
	private String name;
}
