package com.bank.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TransferPaymentDto implements Serializable {

	@NotNull
	@JsonProperty("avaiable_balance")
	protected Double avaiableBalance;
	
	@NotNull
	@JsonProperty("amount_paid")
	protected Double amountPaid;
	
	@NotNull
	@JsonProperty("is_enable")
	protected Boolean isEnable;

	private static final long serialVersionUID = -411633906562163095L;
}