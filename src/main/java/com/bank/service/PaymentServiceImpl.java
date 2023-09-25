package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.model.TransferPaymentEntity;
import com.bank.repository.ITransferPaymentRepository;

@Service
@Transactional
public class PaymentServiceImpl implements IPaymentService {

	@Autowired
	private ITransferPaymentRepository repository;

	@Override
	public void altaEntidad(TransferPaymentEntity entity) {
		repository.save(entity);
	}

	@Override
	public void updateTransactionStatus(Boolean newValue, String transactionId) {
		repository.updateTransactionStatus(newValue, transactionId);
	}

	@Override
	public void updateTransactionStatusError(Boolean newValue, String error, String transactionId) {
		repository.updateTransactionStatusError(newValue, error, transactionId);
	}

	@Override
	public TransferPaymentEntity findById(String transactionId) {
		return repository.findById(transactionId).orElseThrow();
	}
}