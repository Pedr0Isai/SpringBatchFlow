package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TRANSFER_PAYMENT_HISTORY")
public class TransferPaymentEntity {

	@NotNull
	@Column(nullable = false)
	@Id
	private String id;
	
	@NotNull
	@Column(name = "avaiable_balance", nullable = false)
	private Double avaiableBalance;
	
	@NotNull
	@Column(name = "amount_paid", nullable = false)
	private Double amountPaid;
	
	@NotNull
	@Column(name = "is_enable", nullable = false)
	private Boolean isEnable;
	
	@NotNull
	@Column(name = "is_processed", nullable = false)
	private Boolean isProcessed;
	
	private String error;
}