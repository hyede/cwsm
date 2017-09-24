package com.cwsm.platfrom.model.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class ResponseBean<T extends Serializable> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public enum STATUS {
        failed, succeed
    }

    private Date responseTime;
    private STATUS responseStatus;
    private Object responseBody;
    private String errorCode;

    public ResponseBean() {
        responseTime = Calendar.getInstance().getTime();
    }

    public ResponseBean(STATUS status, String code, Object body) {
        responseTime = Calendar.getInstance().getTime();
        this.responseStatus = status;
        this.errorCode = code;
        this.responseBody = body;
    }

    public ResponseBean(STATUS status) {
        this(status, null, null);
    }

    public ResponseBean(STATUS status, String code) {
        this(status, code, null);
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public STATUS getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(STATUS responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String code) {
        this.errorCode = code;
    }

}
