package com.bank.service;

import com.bank.model.TransferPaymentEntity;

public interface IPaymentService {
	
	void altaEntidad(TransferPaymentEntity entity);
	
	void updateTransactionStatus(Boolean newValue, String transactionId);
	
	void updateTransactionStatusError(Boolean newValue, String error, String transactionId);

	TransferPaymentEntity findById(String transactionId);
	
}