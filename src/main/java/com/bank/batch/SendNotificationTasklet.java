package com.bank.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendNotificationTasklet implements Tasklet{

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		/** Desde el ChunkContext se obtiene el transactionId. Definido en PaymentControllerImpl */
		String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");
		
		// Lógica para enviar la notificación.
		
		log.info("Se ha enviado una notificación al cliente para la transacción {}", transactionId);
		
		return RepeatStatus.FINISHED;
	}

}
