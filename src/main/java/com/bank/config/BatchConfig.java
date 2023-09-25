package com.bank.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bank.batch.CancelTransactionTasklet;
import com.bank.batch.ProcessPaymentTasklet;
import com.bank.batch.SendNotificationTasklet;
import com.bank.batch.ValidateAccountTasklet;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilder;

	@Autowired
	private StepBuilderFactory stepBuilder;

	@Bean
	ValidateAccountTasklet validateAccountTasklet() {
		return new ValidateAccountTasklet();
	}

	@Bean
	ProcessPaymentTasklet processPaymentTasklet() {
		return new ProcessPaymentTasklet();
	}

	@Bean
	CancelTransactionTasklet cancelTransactionTasklet() {
		return new CancelTransactionTasklet();
	}

	@Bean
	SendNotificationTasklet sendNotificationTasklet() {
		return new SendNotificationTasklet();
	}

	/**
	 * JobScope define que el Bean esta vigente mientras el job este corriendo, al
	 * terminar el job se elimina el Bean
	 */
	@Bean
	@JobScope
	Step validateAccountStep() {
		return stepBuilder
				/** Se define nombre del Step */
				.get("validateAccountStep")
				/** Se agrega el tasklet de acuerdo al step */
				.tasklet(validateAccountTasklet()).build();
	}

	@Bean
	Step processPaymentStep() {
		return stepBuilder
				/** Se define nombre del Step */
				.get("processPaymentStep")
				/** Se agrega el tasklet de acuerdo al step */
				.tasklet(processPaymentTasklet()).build();
	}

	@Bean
	Step cancelTransactionStep() {
		return stepBuilder
				/** Se define nombre del Step */
				.get("cancelTransactionStep")
				/** Se agrega el tasklet de acuerdo al step */
				.tasklet(cancelTransactionTasklet()).build();
	}

	@Bean
	Step sendNotificationStep() {
		return stepBuilder
				/** Se define nombre del Step */
				.get("sendNotificationStep")
				/** Se agrega el tasklet de acuerdo al step */
				.tasklet(sendNotificationTasklet()).build();
	}

	@Bean
	Job transactionPaymentJob() {
		return jobBuilder
				/** Se define nombre del Job */
				.get("transactionPaymentJob")
				/** Se define el Step inicial */
				.start(validateAccountStep())
				/**
				 * Si el estado de salida de validateStep es 'VALID', continua en
				 * processPaymentStep
				 */
				.on("VALID").to(processPaymentStep())
				/** Obtenemos la salida del primer Step, 'from' no lo vuelve a ejecutar */
				.from(validateAccountStep())
				/**
				 * Si el estado de salida de validateStep es 'INVALID', continua a
				 * cancelTransactionStep
				 */
				.on("INVALID").to(cancelTransactionStep())
				/** Continua el Step que envia notificación */
				.next(sendNotificationStep()).end().build();
	}
}
