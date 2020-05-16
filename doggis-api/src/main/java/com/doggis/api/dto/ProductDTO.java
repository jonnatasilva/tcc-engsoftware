package com.doggis.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ProductDTO implements Serializable {
	
	private static final long serialVersionUID = -2016188058733343042L;
	
	private Long productId;
	@NotEmpty(message = "Product name is missing")
	private String name;
	
	@NotNull(message = "Price is missing")
	@Min(value = 1, message = "Price must be greater than 0")
	private BigDecimal price;

	private String specifications;
	
	@NotNull(message = "Product manufacturer is missing")
	@Valid
	private ManufacturerDTO manufacturer;
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private LocalDateTime createdAt;
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private LocalDateTime updatedAt;
}
