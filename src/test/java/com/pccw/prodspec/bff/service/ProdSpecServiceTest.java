package com.pccw.prodspec.bff.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.prodspec.bff.dto.ProductSpecificationDto;
import com.pccw.prodspec.bff.serviceimpl.ProductSpecificationRestServiceImpl;

/**
 * This class is the service implementation Test class
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class ProdSpecServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ProductSpecificationRestServiceImpl prodService;

	@Mock
	Environment mockEnvironment;

	@BeforeEach
	public void setUp() {
		when(mockEnvironment.getProperty("prodspec.bizcomp.url")).thenReturn("http://localhost");
		when(mockEnvironment.getProperty("prodspec.bizcomp.port")).thenReturn("8083");
	}

	@Autowired
	private ResourceLoader resourceLoader;
	ObjectMapper obj = new ObjectMapper();

	String getFile(String location) throws Exception {
		Resource resource = resourceLoader.getResource(location);
		InputStream input = resource.getInputStream();
		byte[] bdata = FileCopyUtils.copyToByteArray(input);
		String data = new String(bdata, StandardCharsets.UTF_8);
		return data;
	}


	/*
	 * RestTemplate.exchange(String url, HttpMethod method,
	 * 
	 * @Nullable HttpEntity<?> requestEntity,
	 * ParameterizedTypeReference<List<ProductSpecificationDto>> responseType,
	 * Object... uriVariables)
	 */
	@Test
	void getAllProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {

		List<ProductSpecificationDto> product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<ProductSpecificationDto>>() {
				});
		System.out.println("SIZE: " + product.size());

		ResponseEntity<List<ProductSpecificationDto>> resp = new ResponseEntity<>(product, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.any(ParameterizedTypeReference.class))).thenReturn(resp);

		Map<String, Object> map = new HashMap<>();
		map.put("fields", "field");
		map.put("offset", 1);
		map.put("limit", 2);
		List<ProductSpecificationDto> result = prodService.listProductSpecifications(null, map);
		assertNotNull(HttpStatus.OK);
		assertEquals(2, result.size());
	}

	@Test
	void getAllProdSpecExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {

		List<ProductSpecificationDto> product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<ProductSpecificationDto>>() {
				});
		System.out.println("SIZE: " + product.size());

		ResponseEntity<List<ProductSpecificationDto>> resp = new ResponseEntity<>(product, HttpStatus.INTERNAL_SERVER_ERROR);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.any(ParameterizedTypeReference.class))).thenReturn(resp);

		Map<String, Object> map = new HashMap<>();
		map.put("fields", "field");
		map.put("offset", 1);
		map.put("limit", 2);
		List<ProductSpecificationDto> result = prodService.listProductSpecifications(null, map);
		assertNotNull(HttpStatus.OK);
		//assertEquals(2, result.size());
	}
	@Test
	void saveProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {
		ProductSpecificationDto product = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		System.out.println("SIZE: " + product);

		ResponseEntity<ProductSpecificationDto> resp = new ResponseEntity<>(product, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<ProductSpecificationDto>>any())).thenReturn(resp);

		ProductSpecificationDto result = prodService.createProductSpecification(product);
		assertNotNull(HttpStatus.OK);
		assertEquals(product, result);
	}

	@Test
	void retrieveProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {

		ProductSpecificationDto product = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		System.out.println("SIZE: " + product);

		ResponseEntity<ProductSpecificationDto> resp = new ResponseEntity<>(product, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<ProductSpecificationDto>>any())).thenReturn(resp);

		Map<String, Object> pathParams=new HashMap<>();
		pathParams.put("id","3");
		Map<String, Object> queryParams=new HashMap<>();
		queryParams.put("fields","field");
		ProductSpecificationDto result = prodService.retrieveProductSpecificationById(pathParams, queryParams);
		assertNotNull(HttpStatus.OK);
		assertEquals(product, result);
	}

	@Test
	void updateProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {

		ProductSpecificationDto product = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		System.out.println("SIZE: " + product);

		ResponseEntity<ProductSpecificationDto> resp = new ResponseEntity<>(product, HttpStatus.OK);

		when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class),
				Mockito.<Class<ProductSpecificationDto>>any())).thenReturn(resp);
		Map<String, Object> pathParams=new HashMap<>();
		pathParams.put("id","3");
		ProductSpecificationDto result = prodService.updateProductSpecification(pathParams, product);
		assertNotNull(HttpStatus.OK);
		assertEquals(product, result);
	}

	@Test
	void deleteProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {
		ProductSpecificationDto product = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		System.out.println("SIZE: " + product);

		ResponseEntity<ProductSpecificationDto> resp = new ResponseEntity<>(product, HttpStatus.OK);

		Mockito.doNothing().when(restTemplate).delete(Mockito.anyString());

		Map<String, Object> pathParams=new HashMap<>();
		pathParams.put("id","3");
		prodService.deleteProductSpecification(pathParams);
		assertNotNull(HttpStatus.OK);

	}
}
