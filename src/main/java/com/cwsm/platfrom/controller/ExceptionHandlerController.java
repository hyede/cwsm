package com.cwsm.platfrom.controller;

import com.cwsm.platfrom.exception.ApplicationException;
import com.cwsm.platfrom.exception.ErrorCode;
import com.cwsm.platfrom.exception.ServiceException;
import com.cwsm.platfrom.model.bean.ResponseBean;
import com.cwsm.platfrom.model.bean.ValidationErrorBean;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ExceptionHandlerController {



	@ExceptionHandler
	@ResponseBody
	public ResponseBean handleException(Exception e) throws Exception {
		ResponseBean resp = new ResponseBean(ResponseBean.STATUS.failed);
		if(e instanceof MethodArgumentNotValidException) {
			List<ValidationErrorBean> validationErrors = new ArrayList<> ();
			MethodArgumentNotValidException validError = (MethodArgumentNotValidException) e;
			List<FieldError> fieldErrors = validError.getBindingResult().getFieldErrors();
			for(FieldError fe : fieldErrors) {
				ValidationErrorBean validationError = new ValidationErrorBean();
				validationError.setFiledName(fe.getField());
				validationError.setRejectedValue(fe.getRejectedValue());
				validationError.setMessage(fe.getDefaultMessage());
				validationErrors.add(validationError);
			}
			resp.setResponseBody(validationErrors);
			resp.setErrorCode(ErrorCode.validation_error.toString());
		} else if(e instanceof ServiceException) {
			resp.setErrorCode(((ServiceException) e).getErrorCode().toString());
		}  else {
			throw new ApplicationException(e);
		}
		return resp;
	}
}
