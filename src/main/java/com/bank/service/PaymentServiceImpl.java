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
}