package com.gz.tzreport.uitls;

public class JsonDTO {

    private boolean success;
    private int msgcode;
    private String msg;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonDTO(){

    }

    public JsonDTO(boolean success,int msgcode,String msg,Object data){
        super();
        this.success = success;
        this.msgcode = msgcode;
        this.msg = msg;
        this.data = data;
    }

    public void setJsonDTO(boolean success,int msgcode,String msg,Object data){
        this.success = success;
        this.msgcode = msgcode;
        this.msg = msg;
        this.data = data;
    }

}
