package com.cwsm.platfrom.exception;



/**
 * Created by Ming on 5/16/17.
 */
public class ApplicationException extends Exception {

    private ErrorCode errorCode;

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}


