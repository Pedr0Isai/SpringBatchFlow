package com.bank.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bank.dto.ResponseDto;
import com.bank.dto.TransferPaymentDto;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/v1/payment")
public interface IPaymentController {
	
	@PostMapping(value =  "/transfer" ,  produces = { "application/hal+json" })
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = ResponseDto.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDto.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = ResponseDto.class), 
			@ApiResponse(code = 403, message = "Access denied", response = ResponseDto.class),
			@ApiResponse(code = 500, message = "Failure", response = ResponseDto.class) })
	public ResponseEntity<ResponseDto> transferPayment(@RequestBody @Valid TransferPaymentDto dto);
	
}