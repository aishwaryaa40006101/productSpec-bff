package com.pccw.prodspec.bff.exceptions;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.pccw.prodspec.bff.ProdspecServiceBffApplication;
import com.pccw.prodspec.bff.exception.BizCompServiceException;
import com.pccw.prodspec.bff.exception.ParameterMissingException;

/**
 * This class is used to test ParameterMissingException
 * 
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)

class ParameterMissingExceptionTest {
	@Test
	void bizcompServiceExceptionTestCases() throws Exception {
		ParameterMissingException bizcompServiceException = new ParameterMissingException(",'/' missing",
				"Missing '/' parameter", HttpStatus.NOT_FOUND);
		assertThat(bizcompServiceException.getDetails()).isEqualTo("Missing '/' parameter");
		assertThat(bizcompServiceException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
