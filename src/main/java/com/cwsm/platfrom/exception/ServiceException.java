package com.cwsm.platfrom.exception;



public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	public ServiceException() {
		super();
	}
	
	public ServiceException(ErrorCode code) {
		this.errorCode = code;
	}
	
	public ServiceException(String desc) {
		super(desc);
	}
	
	public ServiceException(ErrorCode code, String desc) {
		super(desc);
		this.errorCode = code;
	}
	
	public ServiceException(ErrorCode code, Exception e) {
		super(e);
		this.errorCode = code;
	}
	
	public ServiceException(ErrorCode code, String desc, Exception e) {
		super(desc, e);
		this.errorCode = code;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode code) {
		this.errorCode = code;
	}

}
