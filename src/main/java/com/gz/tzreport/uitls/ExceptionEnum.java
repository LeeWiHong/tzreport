package com.gz.tzreport.uitls;

import org.springframework.http.HttpStatus;

/**
* @description: 异常枚举类
*
* @return: 
**/
public enum  ExceptionEnum {

//    跟数据库CRUD有关的一些自定义代码
    ADD_DATA_SUCCESS(20000,"操作成功",HttpStatus.OK),
    QUERARY_DATA_SUCCESS(20001,"查询成功",HttpStatus.OK),
    INSERT_DATA_SUCCESS(20002,"插入成功",HttpStatus.OK),
    UPDATE_DATA_SUCCESS(20003,"更新数据成功",HttpStatus.OK),
    DELETE_DATA_SUCCESS(20004,"删除数据成功",HttpStatus.OK),
    QUERARY_DATA_FAILURE(20010,"查询失败",HttpStatus.BAD_REQUEST),
    INSERT_DATA_FAILURE(20020,"插入失败",HttpStatus.BAD_REQUEST),
    UPDATE_DATA_FAILURE(20021,"更新失败",HttpStatus.BAD_REQUEST),
    ADD_DATA_FAILURE(20022,"操作失败",HttpStatus.BAD_REQUEST),

//    MOB短信验证的一些验证码
    MOB_SUCCESS(21000,"验证成功",HttpStatus.OK),
    MOB_APPKEY_NULL(21001,"AppKey为空",HttpStatus.BAD_REQUEST),
    MOB_APPKEY_INVALID(21002,"AppKey无效",HttpStatus.BAD_REQUEST),
    MOB_TELEPHONE_NULL(21003,"国家代码或手机号为空",HttpStatus.BAD_REQUEST),
    MOB_TELEPHONE_FORMAT_ERROR(21004,"手机号码格式错误",HttpStatus.BAD_REQUEST),
    MOB_VERIFI_CODENULL(21005,"验证成功",HttpStatus.BAD_REQUEST),
    MOB_VERIFI_REQUEST_FREQUENTLY(21006,"请求验证码频繁",HttpStatus.BAD_REQUEST),
    MOB_VERIFI_CODEERROR(21007,"验证码错误",HttpStatus.BAD_REQUEST),
    MOB_VERIFI_SERVERERROR(21008,"没有打开服务端验证开关",HttpStatus.BAD_REQUEST),
    MOB_UNKNOWN_ERROR(21009,"MOB返回未知的错误",HttpStatus.BAD_REQUEST),


    SYSTEN_GET_KEY_SUCCESS(22000,"获取key成功",HttpStatus.OK),
    SYSTEN_NOT_FOUNDFILE(22001,"未找到文件",HttpStatus.BAD_REQUEST),
    SYSTEN_NO_ALGORITHM(22002,"未找到此类算法",HttpStatus.BAD_REQUEST),
    SYSTEN_ALGORITHM_KEY_INVALID(22003,"无效的算法key",HttpStatus.BAD_REQUEST),
    SYSTEN_ALGORITHM_UNKNOWN_ERROR(22004,"未知的算法错误",HttpStatus.BAD_REQUEST),
    SYSTEN_FILE_UPLOAD_ERROR(22005,"文件流错误",HttpStatus.BAD_REQUEST),

    //    用户登录有关的一些自定义代码
    LOGIN_USER_SUCCESS(10000,"登录成功",HttpStatus.OK),
    LOGIN_USERNAME_ERROR(10001,"用户名错误",HttpStatus.BAD_REQUEST),
    LOGIN_USERNAME_UNSOPPORT(10002,"用户名不合规",HttpStatus.BAD_REQUEST),
    LOGIN_PASSWORD_UNFIT(10003,"两个密码不相同",HttpStatus.BAD_REQUEST),
    LOGIN_TOKEN_NULL(10005,"无token，请重新登录",HttpStatus.BAD_REQUEST),
    LOGIN_TOKEN_INVALID(10006,"token无效",HttpStatus.BAD_REQUEST),
    LOGIN_TOKEN_UNDECODE(10007,"token解码无效",HttpStatus.BAD_REQUEST),
    LOGIN_PASSWORD_FORMAT_ERROR(10008,"密码格式不正确",HttpStatus.BAD_REQUEST),
    LOGINOUT_USER_SUCCESS(10009,"退出成功",HttpStatus.OK),
    LOGINOUT_USER_FAILURE(10009,"退出失败",HttpStatus.BAD_REQUEST),



    //    注册相关的一些自定义代码
    REGISTER_USER_SUCCESS(10004,"注册成功",HttpStatus.OK),

    LOGIN_PASSWORD_ERROR(10010,"登录密码错误",HttpStatus.BAD_REQUEST),
    LOGIN_VERIFICODE_ERROR(10011,"验证码错误",HttpStatus.BAD_REQUEST),
    LOGIN_TELEPHONE_ERROR(10021,"手机号错误",HttpStatus.BAD_REQUEST),
    LOGIN_TELEPHONE_NOTSUPOORT(10022,"手机号不是中国大陆或格式不正确",HttpStatus.NOT_ACCEPTABLE),
    LOGIN_TELEPHONE_HASEXISTED(10023,"手机号已经注册",HttpStatus.BAD_REQUEST),
    LOGIN_USER_NOTEXISTED(10024,"用户不存在",HttpStatus.BAD_REQUEST),
    LOGIN_TELEPHONE_NOTEXISTED(10025,"手机号不存在",HttpStatus.BAD_REQUEST),

    ;

    private int msgcode;
    private String msgdesc;
    private HttpStatus httpStatus;


    ExceptionEnum(int msgcode, String msgdesc, HttpStatus status) {
        this.msgcode = msgcode;
        this.msgdesc = msgdesc;
        this.httpStatus = status;
    }

    public int getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(int msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsgdesc() {
        return msgdesc;
    }

    public void setMsgdesc(String msgdesc) {
        this.msgdesc = msgdesc;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
