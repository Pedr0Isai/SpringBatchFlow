package com.bank.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.bank.model.TransferPaymentEntity;
import com.bank.service.IPaymentService;

/** Clase que valida la cuenta */
public class ValidateAccountTasklet implements Tasklet {

	@Autowired
	private IPaymentService service;
	
	/**
	 * Ejecuta los pasos del tasklet.
	 *
	 * @param contribution. Setea los estados de salida de cada Step 
	 * @param chunkContext. Sirve para acceder al contexto del flujo
	 * @return the repeat status
	 * @throws Exception the exception
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		ExitStatus status = new ExitStatus("VALID");
		/** Desde el ChunkContext se obtiene el transactionId. Definido en PaymentControllerImpl */
		String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");
		
		/** Busca el pago y si no esta, arroja Exception*/
		TransferPaymentEntity entity = service.findById(transactionId);
		
		if(!entity.getIsEnable().booleanValue()) { 
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("message", "Error: cuenta inactiva.");
			status = new ExitStatus("INVALID");
		}
		if(entity.getAmountPaid() > entity.getAvaiableBalance()) {
			chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("message", "Error: saldo insuficiente.");
			status = new ExitStatus("INVALID");
		}
		
		contribution.setExitStatus(status);

		/** Termina el tasklet de validación de cuenta*/
		return RepeatStatus.FINISHED;
	}

}
