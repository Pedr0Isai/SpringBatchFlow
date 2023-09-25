package com.bank.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.ResponseDto;
import com.bank.dto.TransferPaymentDto;
import com.bank.model.TransferPaymentEntity;
import com.bank.service.IPaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value="payments",description = "servicios que realizan transferencia de pago")
@Slf4j
@RestController
public class PaymentControllerImpl implements IPaymentController {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Autowired
	private JobExplorer jobExplorer;
	
	@Autowired
	private IPaymentService service;
	
	@Override
	@ApiOperation(value = "Servicio realiza la transferencia de pago", notes = "Devuelve el resultado de la operación")
	public ResponseEntity<ResponseDto> transferPayment(TransferPaymentDto dto) {
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
		
		ResponseDto response = new ResponseDto(HttpStatus.OK.value(), request.getRequestURI());
		response.setDetalles(info);
		
		try {
			jobLauncher.run(job, getJobParameters(transactionId));
			/** Obtén la instancia del Job por su nombre y JobParameters */
	        JobInstance jobInstance = jobExplorer.getLastJobInstance(job.getName());
	        if(jobInstance != null) {
	        	/** Obtén la información del ultimo Job a través del JobExplorer */
		        JobExecution jobExecution = jobExplorer.getLastJobExecution(jobInstance);
		        if (jobExecution != null) {
		        	String errorMessage = (String) jobExecution.getExecutionContext().get("message");
		            if (errorMessage != null) {
		            	Map<String, Object> errors = new HashMap<>();
		    			errors.put("error", errorMessage);
		    			response.setError(errors);
		    			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		    			response.setMensaje(null);
		    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		            }
		        }
	        }
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}
	
	private JobParameters getJobParameters(String transactionId) {
		return new JobParametersBuilder()
				.addString("ID", UUID.randomUUID().toString())
				.addString("transactionId", transactionId)
				.toJobParameters();
	}

}