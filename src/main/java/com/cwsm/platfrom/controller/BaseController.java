package com.cwsm.platfrom.controller;


import com.cwsm.platfrom.model.bean.ResponseBean;

public abstract class BaseController {


    protected ResponseBean successResponse(Object body) {
        ResponseBean resp = new ResponseBean(ResponseBean.STATUS.succeed);
        resp.setResponseBody(body);
        return resp;

    }

    protected ResponseBean successResponse(Object body, String code) {
        ResponseBean resp = new ResponseBean(ResponseBean.STATUS.succeed);
        resp.setResponseBody(body);
        resp.setErrorCode(code);
        return resp;

    }

    protected ResponseBean successResponse() {
        ResponseBean resp = new ResponseBean(ResponseBean.STATUS.succeed);
        return resp;

    }
}
