package com.animals.care.animalscare.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author jonnatas
 *
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDTO implements Serializable {
	
	private static final long serialVersionUID = -1838104096467114885L;
	
	private Long manufacturerId;
	@NotEmpty(message = "Manufacturer name is missing")
	private String name;
}
