package com.gz.tzreport.uitls;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
* @description: 用来监听处理全局异常
*
* @return: 
**/

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

//    处理自定义异常
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomerException(CustomException ex){
        final JsonDTO customeJsonDTD = new JsonDTO(false,ex.getMsgCode(),ex.getLocalizedMessage(),new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),ex.getHttpStatus());
    }


//    处理通用异常,这里举例说明如何覆盖处理 请求方法不支持的异类
    @Override
    protected  ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"httpRequestMethodNotSupported",new ArrayList<>());
        System.out.println("我是全局处理请求方法不支持方法");
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

//    重写系统的404方法
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"NoHandlerFoundException",new ArrayList<>());
        System.out.println("我是全局处理404异常方法");
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }
//    对http媒体支持类型未重写
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }

        return handleExceptionInternal(ex, null, headers, status, request);
    }
//    对http媒体接受类型未重写
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex, null, headers, status, request);
    }
    protected ResponseEntity<Object> handleMissingPathVariable(
            MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"MissingPathVariableException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

//
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"MissingServletRequestParameterException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"ServletRequestBindingException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleConversionNotSupported(
            ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"ConversionNotSupportedException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"TypeMismatchException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"HttpMessageNotReadableException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"HttpMessageNotWritableException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"MethodArgumentNotValidException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"MissingServletRequestPartException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"BindException",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

//    未重写
    @Nullable
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        if (webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Async request timed out");
                }
                return null;
            }
        }

        return handleExceptionInternal(ex, null, headers, status, webRequest);
    }
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final JsonDTO customeJsonDTD = new JsonDTO(false,status.value(),"内部服务器错误",new ArrayList<>());
        return new ResponseEntity<Object>(customeJsonDTD,new HttpHeaders(),status);
    }

}
