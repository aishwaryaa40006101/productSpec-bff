package com.pccw.prodspec.bff.serviceimpl;

import com.pccw.prodspec.bff.constants.Constants;
import com.pccw.prodspec.bff.dto.ProductSpecificationDto;
import com.pccw.prodspec.bff.exception.CustomException;
import com.pccw.prodspec.bff.service.ProductSpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 20023424 This class is the client service implementation class which
 *         interacts with bizComp service to return the expected result
 */

@Slf4j
@Service
public class ProductSpecificationRestServiceImpl implements ProductSpecificationService {

	/*
	 * @Bean public RestTemplate restTemplate() { return new RestTemplate(); }
	 */

	@Autowired
	Environment env;

	@Autowired
	RestTemplate restTemplate;

	/**
	 * List or find ProductSpecification objects.
	 *
	 * @param id
	 * @param fields
	 * @return ProductSpecification
	 */
	@Override
	public List<ProductSpecificationDto> listProductSpecifications(Map<String, Object> pathParams,
			Map<String, Object> queryParams) {
		List<ProductSpecificationDto> productSpecLst = new ArrayList<>();
		try {
//            RestTemplate restTemplate = new RestTemplate();
			log.info("listProductSpecifications in BFF");
			HttpEntity<List<ProductSpecificationDto>> prodSpecEntity = new HttpEntity<>(httpHeaderConfig());
			System.out.println(getURI(pathParams, queryParams));

			ResponseEntity<List<ProductSpecificationDto>> response = restTemplate.exchange(
					getURI(pathParams, queryParams), HttpMethod.GET, prodSpecEntity,
					new ParameterizedTypeReference<List<ProductSpecificationDto>>() {
					});
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				productSpecLst = response.getBody();
				System.out.println("size oF : " + productSpecLst.size());
			}
		} catch (CustomException ce) {
			log.error(ce.getMessage(), ce);
			throw ce;
		}
		return productSpecLst;
	}

	/**
	 * Creates a ProductSpecification.
	 *
	 * @param productSpec
	 * @return ProductSpecification
	 */
	@Override
	public ProductSpecificationDto createProductSpecification(ProductSpecificationDto productSpec) {
		ProductSpecificationDto prodSpec = null;
		try {
			log.info("createProductSpecification in BFF");
//            RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ProductSpecificationDto> prodSpecEntity = new HttpEntity<>(productSpec);
			messageConverter(restTemplate);
			ResponseEntity<ProductSpecificationDto> prodSpecRes = restTemplate.exchange(getURI(null, null),
					HttpMethod.POST, prodSpecEntity, ProductSpecificationDto.class);
			if (prodSpecRes.getStatusCode() == HttpStatus.OK && prodSpecRes.getBody() != null) {
				prodSpec = prodSpecRes.getBody();
			}
		} catch (CustomException ce) {
			log.error(ce.getMessage(), ce);
			throw ce;
		}
		return prodSpec;
	}

	/**
	 * Retrieves a ProductSpecification by ID.
	 *
	 * @param id
	 * @param fields
	 * @return ProductSpecification
	 */
	@Override
	public ProductSpecificationDto retrieveProductSpecificationById(Map<String, Object> pathParams,
			Map<String, Object> queryParams) {
		ProductSpecificationDto prodSpec = null;
		try {
//            RestTemplate restTemplate = new RestTemplate();
			log.info("retrieveProductSpecificationById in BFF");
			HttpEntity<ProductSpecificationDto> prodSpecEntity = new HttpEntity<>(httpHeaderConfig());
			ResponseEntity<ProductSpecificationDto> response = restTemplate.exchange(getURI(pathParams, queryParams),
					HttpMethod.GET, prodSpecEntity, ProductSpecificationDto.class);
			if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
				prodSpec = response.getBody();
			}
		} catch (CustomException ce) {
			log.error(ce.getMessage(), ce);
			throw ce;
		}
		return prodSpec;
	}

	/**
	 * Updates partially a ProductSpecification.
	 *
	 * @param id
	 * @param productSpec
	 * @return ProductSpecification
	 */
	@Override
	public ProductSpecificationDto updateProductSpecification(Map<String, Object> pathParams,
			ProductSpecificationDto productSpec) {
		ProductSpecificationDto prodSpec = null;
		try {
//            RestTemplate restTemplate = new RestTemplate();
			log.info("updateProductSpecification in BFF");
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			HttpEntity<ProductSpecificationDto> prodSpecEntity = new HttpEntity<>(productSpec, httpHeaderConfig());
			messageConverter(restTemplate);
			ResponseEntity<ProductSpecificationDto> prodSpecRes = restTemplate.exchange(getURI(pathParams, null),
					HttpMethod.PATCH, prodSpecEntity, ProductSpecificationDto.class);
			if (prodSpecRes.getStatusCode() == HttpStatus.OK && prodSpecRes.getBody() != null) {
				prodSpec = prodSpecRes.getBody();
			}
		} catch (CustomException ce) {
			log.error(ce.getMessage(), ce);
			throw ce;
		}
		return prodSpec;
	}

	/**
	 * Deletes a ProductSpecification.
	 *
	 * @param id
	 */
	@Override
	public void deleteProductSpecification(Map<String, Object> pathParams) {
		try {
//            RestTemplate restTemplate = new RestTemplate();
			log.info("updateProductSpecification in BFF");
			messageConverter(restTemplate);
			restTemplate.delete(getURI(pathParams, null));
		} catch (CustomException ce) {
			log.error(ce.getMessage(), ce);
			throw ce;
		}
	}

	// TODO - To move below methods to Utility class

	/**
	 * @param pathparams
	 * @param queryParams
	 * @return
	 */
	private String getURI(Map<String, Object> pathparams, Map<String, Object> queryParams) {

		
		log.info(env.getProperty("prodspec.bizcomp.url"));

		StringBuilder url = new StringBuilder(env.getProperty("prodspec.bizcomp.url") + ":"
				+ env.getProperty("prodspec.bizcomp.port") + "/" + Constants.PRODSPECSERVIC);

		if (null != pathparams && !pathparams.isEmpty()) {
			for (Map.Entry<String, Object> entry : pathparams.entrySet()) {
				url.append("/" + entry.getValue());
			}
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url.toString());
		if (null != queryParams && !queryParams.isEmpty()) {
			for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
				builder.queryParam(entry.getKey(), entry.getValue());
			}
		}

		return builder.toUriString();

	}

	private HttpHeaders httpHeaderConfig() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return requestHeaders;
	}

	private void messageConverter(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		// Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM,
				MediaType.APPLICATION_FORM_URLENCODED));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}

}
