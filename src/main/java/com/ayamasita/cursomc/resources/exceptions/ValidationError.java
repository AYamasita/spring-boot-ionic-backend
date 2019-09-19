package com.ayamasita.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<FieldMessage>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		
	}

	public List<FieldMessage> getErrors() {
		return errors;  //retorna a lista de erros
	}
	//add one Error
	public void addError( String fieldName, String message) {
		errors.add(new FieldMessage(fieldName,message));
	}
	

}
