package com.bank.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.ResponseDto;
import com.bank.dto.TransferPaymentDto;
import com.bank.model.TransferPaymentEntity;
import com.bank.service.IPaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="payments",description = "servicios que realizan transferencia de pago")
@RestController
public class PaymentControllerImpl implements IPaymentController {

	@Autowired
	private IPaymentService service;

	@Override
	@ApiOperation(value = "Servicio realiza la transferencia de pago", notes = "Devuelve el resultado de la operación")
	public ResponseEntity<ResponseDto> transferPayment(TransferPaymentDto dto) {
		ResponseDto response = new ResponseDto();
		String transactionId = UUID.randomUUID().toString();
		TransferPaymentEntity transferPaymentEntity = TransferPaymentEntity.builder()
				.id(transactionId)
				.avaiableBalance(dto.getAvaiableBalance())
				.amountPaid(dto.getAmountPaid())
				.isEnable(dto.getIsEnable())
				.isProcessed(false)
				.build();
		
		service.altaEntidad(transferPaymentEntity);
		Map<String, Object> info = new HashMap<>();
		info.put("transaction_id", transactionId);
		response.setDetalles(info);
		
		return ResponseEntity.ok(response);
	}

}