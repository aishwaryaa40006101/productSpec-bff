package com.pccw.prodspec.bff.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.pccw.prodspec.bff.ProdspecServiceBffApplication;
import com.pccw.prodspec.bff.constants.Constants;
import com.pccw.prodspec.bff.exception.CustomException;
import com.pccw.prodspec.bff.exception.CustomExceptionHandler;

/**
 * This class is used to test CustomeExceptionHandler 
 * 
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

	@Autowired
	CustomExceptionHandler customExceptionHandler;

	@Test
	void customExceptionHandlerTestCases() throws Exception {

		CustomException ex = new CustomException("No Records", "NodataFound");

		
		  Map<String, String> res = new HashMap<>(); res.put(Constants.MESSAGE,
		  ex.getMessage()); res.put(Constants.DETAILS, ex.getDetails());
		  ResponseEntity<Object> obj = new ResponseEntity<>(res,
		  HttpStatus.INTERNAL_SERVER_ERROR);
		 
		//Mockito.when(customExceptionHandler.handleInternalServerErrorException(ex)).thenReturn(obj);
		ResponseEntity<Object> result=customExceptionHandler.handleInternalServerErrorException(ex);
		//System.out.println(result.getBody());		System.out.println(res);

		assertEquals(result.getBody(),res);

	}

}
