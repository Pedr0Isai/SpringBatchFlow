package com.bank.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.bank.service.IPaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessPaymentTasklet implements Tasklet{

	@Autowired
	private IPaymentService service;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		/** Desde el ChunkContext se obtiene el transactionId. Definido en PaymentControllerImpl */
		String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");
		
		log.info("---------> Se procesa pago de la transacción '{}' exitosamente <---------", transactionId);
		/*** Actualiza el estado de la entidad a procesado */
		service.updateTransactionStatus(true, transactionId);
		
		/** Retornamos FINISHED por ser el ultimo paso de este flujo*/
		return RepeatStatus.FINISHED;
	}

}
