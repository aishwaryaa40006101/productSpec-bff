package com.pccw.prodspec.bff.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pccw.prodspec.bff.dto.ProductSpecificationDto;
import com.pccw.prodspec.bff.exception.CustomException;
import com.pccw.prodspec.bff.serviceimpl.ProductSpecificationRestServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class ProdSpecControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	ProductSpecificationRestServiceImpl prodSpecService;

	@BeforeEach
	public void setUp() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
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

	/**
	 * Test Case For getting all ProductSpecification list
	 *
	 * @param fields
	 * @param offset
	 * @param limit
	 */

	@Test
	void getAllProdSpecTest() throws Exception {

		List<ProductSpecificationDto> product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<ProductSpecificationDto>>() {
				});
		when(prodSpecService.listProductSpecifications(Mockito.any(), Mockito.any())).thenReturn(product);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/productSpecification?fields=fields&offset=0&limit=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
				.andExpect(content().json(getFile("classpath:checkAllData.json")));
		assertNotNull(HttpStatus.OK);
	
	}

	@Test
	void getAllProdSpecNullOffsetExceptionTest() throws Exception {

		List<ProductSpecificationDto> product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<ProductSpecificationDto>>() {
				});
		when(prodSpecService.listProductSpecifications(Mockito.any(), Mockito.any())).thenReturn(product);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/productSpecification?fields=fields&limit=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		//assertNotNull(HttpStatus.OK);
	
	}
	@Test
	void getAllProdSpecNullLimitExceptionTest() throws Exception {

		List<ProductSpecificationDto> product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<List<ProductSpecificationDto>>() {
				});
		when(prodSpecService.listProductSpecifications(Mockito.any(), Mockito.any())).thenReturn(product);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/productSpecification?fields=fields&offset=1").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
		//assertNotNull(HttpStatus.OK);
	
	}
	/**
	 * Test Case For creating new ProductSpecification
	 *
	 * @return ProductSpecification
	 */

	@Test
	void saveProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {

		ProductSpecificationDto prodSpec = obj.readValue(getFile("classpath:createData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		when(prodSpecService.createProductSpecification(Mockito.any(ProductSpecificationDto.class)))
				.thenReturn(prodSpec);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productSpecification")
				.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:createData.json"))
				.contentType(MediaType.APPLICATION_JSON);
		 mockMvc.perform(requestBuilder).andDo(print())
				.andExpect(content().json(getFile("classpath:createCheckData.json")));
		assertNotNull(HttpStatus.OK);


	}

	@Test
	void saveProdSpecExceptionTest() throws JsonMappingException, JsonProcessingException, Exception {

		//CustomException ex=new CustomException("");
		ProductSpecificationDto prodSpec = obj.readValue(getFile("classpath:createExceptionData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		when(prodSpecService.createProductSpecification(Mockito.any()))
				.thenThrow(new RuntimeException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productSpecification");
				
		 mockMvc.perform(requestBuilder).andDo(print());
			//	.andExpect(content().json(getFile("classpath:createCheckData.json")));
		assertNotNull(HttpStatus.OK);


	}
	/**
	 * Test Case Retrieving a ProductSpecification by ID.
	 *
	 * @param id
	 * @param fields
	 * @return ProductSpecification
	 */
	@Test
	void retrieveByIdTest() throws JsonMappingException, JsonProcessingException, Exception {
		ProductSpecificationDto[] product = obj.readValue(getFile("classpath:data.json"),
				new TypeReference<ProductSpecificationDto[]>() {
				});
		when(prodSpecService.retrieveProductSpecificationById(Mockito.any(), Mockito.any())).thenReturn(product[1]);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/productSpecification/2&fields=fields")
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
				// .andExpect(jsonPath("$.id",is("2")))
				.andExpect(content().json(getFile("classpath:RetrieveCheckData.json")));
		assertNotNull(HttpStatus.OK);

	}

	/**
	 * Test Case Updating partially a ProductSpecification.
	 *
	 * @param id
	 * @param productSpec
	 * @return ProductSpecification
	 */
	@Test
	void updateprodSecTest() throws JsonMappingException, JsonProcessingException, Exception {
		ProductSpecificationDto prodSpec = obj.readValue(getFile("classpath:updateData.json"),
				new TypeReference<ProductSpecificationDto>() {
				});
		when(prodSpecService.updateProductSpecification(Mockito.any(), Mockito.any(ProductSpecificationDto.class)))
				.thenReturn(prodSpec);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/productSpecification/2");
		this.mockMvc
				.perform(builder.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:updateData.json"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect((ResultMatcher) jsonPath("$.id", is("2"))).andDo(print())
				.andExpect(content().json(getFile("classpath:UpdateCheckData.json")));
		assertNotNull(HttpStatus.OK);


	}

	/**
	 * Test case for Deleting a ProductSpecification.
	 *
	 * @param id
	 */
	@Test
	void deleteProdSpecTest() throws JsonMappingException, JsonProcessingException, Exception {

		Mockito.doNothing().when(prodSpecService).deleteProductSpecification(Mockito.any());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/productSpecification/2");
		this.mockMvc.perform(builder.accept(MediaType.APPLICATION_JSON).content(getFile("classpath:updateData.json"))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		

	}

}
