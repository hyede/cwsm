package com.cwsm.platfrom.model.bean;

public class ValidationErrorBean extends GenericObject {

	/**
     *
	 */
	private static final long serialVersionUID = 1L;
	
	private String filedName;
	private Object rejectedValue;
	private String message;
	
	public ValidationErrorBean() {
		
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
