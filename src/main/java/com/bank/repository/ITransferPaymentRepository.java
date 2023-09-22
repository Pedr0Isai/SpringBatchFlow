package com.bank.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bank.model.TransferPaymentEntity;

@Repository
public interface ITransferPaymentRepository extends CrudRepository<TransferPaymentEntity, String> {

	/** Actualiza el estado de la transacción */
	@Modifying
	@Transactional
	@Query("UPDATE TransferPaymentEntity tpe SET tpe.isProcessed = :newValue WHERE tpe.id = :id ")
	void updateTransactionStatus(@Param("newValue") Boolean newValue, @Param("id") String transactionId);
	
	/** Actualiza el estado de la transacción */
	@Modifying
	@Transactional
	@Query("UPDATE TransferPaymentEntity tpe SET tpe.isProcessed = :newValue, tpe.error = :error WHERE tpe.id = :id ")
	void updateTransactionStatusError(@Param("newValue") Boolean newValue, @Param("error") String error, @Param("id") String transactionId);
}