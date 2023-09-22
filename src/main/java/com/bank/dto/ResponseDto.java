package com.bank.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseDto implements Serializable {

	private LocalDateTime timestamp;
	
	protected int status;
	
	protected Map<String, Object> error;
	
	protected String mensaje;
	
	protected String path;
	
	protected Map<String, Object> detalles;
	
	
	public ResponseDto() {
		this.status = 200;
		this.mensaje = "Operación correcta";
		this.timestamp = LocalDateTime.now();
	}
	
	public ResponseDto(int result, String mensaje) {
		this.status = result;
		this.mensaje = mensaje;
		this.timestamp = LocalDateTime.now();
	}

	private static final long serialVersionUID = 8140207020324123921L;
}