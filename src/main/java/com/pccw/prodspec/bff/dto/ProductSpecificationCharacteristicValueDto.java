package com.pccw.prodspec.bff.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductSpecificationCharacteristicValueDto DTO class
 * 
 * @author 20023424
 *
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class ProductSpecificationCharacteristicValueDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean isDefault = null;
	private String rangeInterval = null;
	private String regex = null;
	private String unitOfMeasure = null;
	private String valueFrom = null;
	private String valueTo = null;
	private String valueType = null;
	private TimePeriodDto validFor = null;
	private AnyDto value = null;
	private String baseType = null;
	private String schemaLocation = null;
	private String type = null;
}
