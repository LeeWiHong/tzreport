package com.gz.tzreport.uitls;

import org.springframework.http.HttpStatus;

/**
* @description: 自定义异常类
*
* @return: 
**/
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;

    private int msgCode;
    public CustomException(HttpStatus httpStatus,int msgCode,String message){
        super(message);
        this.httpStatus = httpStatus;
        this.msgCode = msgCode;
    }

    public CustomException(String message,int errorCode,Exception e){
        super(message,e.getCause());
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }
    
}
