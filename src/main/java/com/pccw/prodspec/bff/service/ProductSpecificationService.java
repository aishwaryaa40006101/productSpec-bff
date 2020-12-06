package com.pccw.prodspec.bff.service;

import java.util.List;
import java.util.Map;

import com.pccw.prodspec.bff.dto.ProductSpecificationDto;

/**
 * @author 20023424
 *
 */
public interface ProductSpecificationService {

	/**
	 * List or find ProductSpecification objects.
	 * 
	 * @return List<ProductSpecification>
	 */
	public List<ProductSpecificationDto> listProductSpecifications(Map<String, Object> pathParams,
			Map<String, Object> queryParams);

	/**
	 * ProductSpecification Creates a ProductSpecification.
	 * 
	 * @param productSpec
	 * @return ProductSpecification
	 */
	public ProductSpecificationDto createProductSpecification(ProductSpecificationDto productSpec);

	/**
	 * Retrieves a ProductSpecification by ID.
	 * 
	 * @param id
	 * @param fields
	 * @return ProductSpecification
	 */
	public ProductSpecificationDto retrieveProductSpecificationById(Map<String, Object> pathParams,
			Map<String, Object> queryParams);

	/**
	 * Updates partially a ProductSpecification.
	 * 
	 * @param id
	 * @param productSpec
	 * @return ProductSpecification
	 */
	public ProductSpecificationDto updateProductSpecification(Map<String, Object> pathParams,
			ProductSpecificationDto productSpec);

	/**
	 * Deletes a ProductSpecification.
	 * 
	 * @param id
	 */
	public void deleteProductSpecification(Map<String, Object> pathParams);

}
