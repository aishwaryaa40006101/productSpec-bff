package com.pccw.prodspec.bff.controller;

import com.pccw.prodspec.bff.constants.Constants;
import com.pccw.prodspec.bff.dto.ProductSpecificationDto;
import com.pccw.prodspec.bff.exception.CustomException;
import com.pccw.prodspec.bff.serviceimpl.ProductSpecificationRestServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is RestController class in ProductSpec BFF
 *
 * @author 20023424
 */
@Slf4j
@RestController
public class ProdSpecBffController {

    @Autowired
    ProductSpecificationRestServiceImpl prodSpecService;

    /**
     * List or find ProductSpecification objects.
     *
     * @param fields
     * @param offset
     * @param limit
     * @return List<ProductSpecification>
     */
    @GetMapping(value = "/productSpecification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductSpecificationDto>> listProductSpecifications(
            @RequestParam(value = "fields", required = false) String fields,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<ProductSpecificationDto> prodSpec = new ArrayList<>();
        try {
            if (null != offset && null == limit) {
                throw new CustomException(Constants.MISSING_LIMIT, Constants.MISSING_LIMIT_MSG);
            }
            if (null != limit && null == offset) {
                throw new CustomException(Constants.MISSING_LIMIT, Constants.MISSING_LIMIT_MSG);
            }
            Map<String, Object> queryParams = new HashMap<>();
            if (null != fields)
                queryParams.put("fields", fields);
            if (null != offset)
                queryParams.put("offset", offset);
            if (null != limit)
                queryParams.put("limit", limit);
            prodSpec = prodSpecService.listProductSpecifications(null, queryParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(prodSpec, HttpStatus.OK);
    }

    /**
     * Creates a ProductSpecification.
     *
     * @param prodSpec
     * @return ProductSpecification
     */
    @PostMapping(value = "/productSpecification", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSpecificationDto> createProductSpecification(
            @RequestBody(required = true) ProductSpecificationDto prodSpec) {
        ProductSpecificationDto prodSpecRes = null;
        try {
            prodSpecRes = prodSpecService.createProductSpecification(prodSpec);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(prodSpecRes, HttpStatus.OK);
    }

    /**
     * Retrieves a ProductSpecification by ID.
     *
     * @param id
     * @param fields
     * @return ProductSpecification
     */
    @GetMapping(value = "/productSpecification/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSpecificationDto> retrieveProductSpecificationById(
            @PathVariable(value = "id", required = true) String id,
            @RequestParam(value = "fields", required = false) String fields) {
        ProductSpecificationDto prodSpecRes = null;
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            Map<String, Object> queryParams = new HashMap<>();
            if (null != fields)
                queryParams.put("fields", fields);
            prodSpecRes = prodSpecService.retrieveProductSpecificationById(pathParams, queryParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(prodSpecRes, HttpStatus.OK);
    }

    /**
     * Updates partially a ProductSpecification.
     *
     * @param id
     * @param prodSpec
     * @return ProductSpecification
     */
    @PatchMapping(value = "/productSpecification/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSpecificationDto> updateProductSpecification(
            @PathVariable(value = "id", required = true) String id,
            @RequestBody(required = true) ProductSpecificationDto prodSpec) {
        ProductSpecificationDto prodSpecRes = null;
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            prodSpecRes = prodSpecService.updateProductSpecification(pathParams, prodSpec);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
        return new ResponseEntity<>(prodSpecRes, HttpStatus.OK);
    }

    /**
     * Deletes a ProductSpecification.
     *
     * @param id
     */
    @DeleteMapping(value = "/productSpecification/{id}")
    public void deleteProductSpecification(@PathVariable(value = "id", required = true) String id) {
        try {
            Map<String, Object> pathParams = new HashMap<>();
            if (null != id)
                pathParams.put("id", id);
            prodSpecService.deleteProductSpecification(pathParams);
        } catch (CustomException ce) {
            log.error(ce.getDetails());
            throw ce;
        }
    }

}
