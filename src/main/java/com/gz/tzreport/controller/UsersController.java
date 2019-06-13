package com.gz.tzreport.controller;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.annotation.SuperAuthorityToken;
import com.gz.tzreport.annotation.UserLoginToken;
import com.gz.tzreport.dao.TbUsersPersonInfo;
import com.gz.tzreport.pojo.TbRoles;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.TbrolesServiceInterface;
import com.gz.tzreport.service.TokenService;
import com.gz.tzreport.service.UsersServiceInterface;
import com.gz.tzreport.uitls.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

@RestController

@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UsersServiceInterface usersServiceInterface;

    @Autowired
    private TbrolesServiceInterface tbrolesServiceInterface;

    @Autowired
    private TokenService tokenService;

    @SuperAuthorityToken
    @RequestMapping("/p1")
    public String test(){
        return "leewihong";
    }

    /**
    * @description: 删除用户操作
    *
    * @return:
    **/
    @SuperAuthorityToken
    @RequestMapping("/delUser")
    public JsonDTO deleteUser(@RequestParam(value = "userid") Integer userid){
        JsonDTO jsonDTO = new JsonDTO();
        if (usersServiceInterface.deleteByPrimaryKey(userid) > 0){
            jsonDTO.setJsonDTO(true,ExceptionEnum.DELETE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.DELETE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.DELETE_DATA_FAILURE.getMsgcode(),ExceptionEnum.DELETE_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


    /**
    * @description: 修改密码接口
    *
    * @return: jsondto
    **/

    @RequestMapping("/updatepassword")
    public JsonDTO ChangePassword(@RequestParam(value = "telephone") String telephone,@RequestParam(value = "code") String code,@RequestParam(value = "newpassword") String newpassword){
        JsonDTO jsonDTO = new JsonDTO();
//        1.查找电话号码用户有没有
        if (usersServiceInterface.selectByTelephone(telephone).size() > 0){
//            2.根据电话号码跟code去验证是否正确
            String result = requestData(telephone,code);
//           验证返回的结果是不是正确的,如果是正确的返回前端的公钥出去与结果
            if (JSON.parseObject(result).get("status").toString().equals("200"))
            {
//               3.解析新密码
                String DescryNewPassword = null;
                try {

                    byte[] NewpasswordbyteArray = Base64.getDecoder().decode(newpassword.getBytes());
                    PrivateKey privateKey = WHEncryptTools.getPemPrivateKey("rsa_private_key.pem", "RSA");
                    DescryNewPassword = WHEncryptTools.RSADecrypt(Hex.encodeHexString(NewpasswordbyteArray), privateKey);
                    TbUsers tbUsers = usersServiceInterface.selectByTelephone(telephone).get(0);
                    String MD5NewPass =WHEncryptTools.MD5Encode(DescryNewPassword, "utf-8");
                    tbUsers.setUserPassword(MD5NewPass);
                    String token = tokenService.getToken(tbUsers);
                    tbUsers.setUserToken(token);
//                    4.更新用户密码,确切的说这个地方应该也把token与publickey给更新一下到前端去的
                    if (usersServiceInterface.updateByPrimaryKey(tbUsers) > 0){
                        HashMap hashMap = new HashMap();
                        hashMap.put("token",token);
                        jsonDTO.setJsonDTO(true,ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgdesc(),hashMap);
                    }
                    else {
                        jsonDTO.setJsonDTO(false,ExceptionEnum.UPDATE_DATA_FAILURE.getMsgcode(),ExceptionEnum.UPDATE_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
                    }
                }
                catch (Exception e){
                    jsonDTO.setJsonDTO(false,ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgdesc(),new ArrayList<>());
                }
            }
            else
                {
                    jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_VERIFICODE_ERROR.getMsgcode(),ExceptionEnum.LOGIN_VERIFICODE_ERROR.getMsgdesc(),new ArrayList<>());
                }
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgcode(),ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


    /**
    * @description: 退出
    *
    * @return:
    **/

    @RequestMapping("/loginout")
    public JsonDTO loginout(@RequestParam(value = "token") String token){
        // 获取 token 中的 user id
        JsonDTO jsonDTO = new JsonDTO();
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new CustomException(ExceptionEnum.LOGIN_TOKEN_UNDECODE.getHttpStatus(),ExceptionEnum.LOGIN_TOKEN_UNDECODE.getMsgcode(),ExceptionEnum.LOGIN_TOKEN_UNDECODE.getMsgdesc());
        }

        TbUsers tbUsers = usersServiceInterface.selectByPrimaryKey(Integer.valueOf(userId));
        if (tbUsers == null) {
            throw new CustomException(ExceptionEnum.LOGIN_USER_NOTEXISTED.getHttpStatus(),ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgcode(),ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgdesc());
        }
        tbUsers.setUserToken(null);
        tbUsers.setOnlineState("1");
        usersServiceInterface.updateTokenByPrimaryKey(tbUsers);
        if (usersServiceInterface.updateTokenByPrimaryKey(tbUsers) > 0){
            jsonDTO.setJsonDTO(true,ExceptionEnum.LOGINOUT_USER_SUCCESS.getMsgcode(),ExceptionEnum.LOGINOUT_USER_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.LOGINOUT_USER_FAILURE.getMsgcode(),ExceptionEnum.LOGINOUT_USER_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


    /**
    * @description: 登录
    *
    * @return:
    **/

    @RequestMapping("/login")
    public JsonDTO login(@RequestParam(value = "telephone") String telephone,@RequestParam(value = "password") String password){
        JsonDTO jsonDTO = new JsonDTO();
        if (ValidatorFormatCheckTools.isPhoneLegal(telephone)){
//            1.根据电话号码查询用户
            TbUsers tbUsers = usersServiceInterface.selectByTelephone(telephone).get(0);
            if (tbUsers != null){
                String MD5Password = null;
//                2.解密传过来的密码
                try {
                    byte[] passwordbyteArray = Base64.getDecoder().decode(password.getBytes());

                    PrivateKey privateKey = WHEncryptTools.getPemPrivateKey("rsa_private_key.pem","RSA");
                    String decrypassword = WHEncryptTools.RSADecrypt(Hex.encodeHexString(passwordbyteArray),privateKey);
                    MD5Password = WHEncryptTools.MD5Encode(decrypassword,"utf-8");
                }
                catch (IOException e){
                    throw new CustomException(ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getHttpStatus(),ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getMsgcode(),ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getMsgdesc());
                }
                catch (NoSuchAlgorithmException e){
                    throw new CustomException(ExceptionEnum.SYSTEN_NO_ALGORITHM.getHttpStatus(),ExceptionEnum.SYSTEN_NO_ALGORITHM.getMsgcode(),ExceptionEnum.SYSTEN_NO_ALGORITHM.getMsgdesc());
                }
                catch (InvalidKeySpecException e){
                    throw new CustomException(ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getHttpStatus(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgdesc());
                }
                catch (Exception e){
                    throw new CustomException(ExceptionEnum.SYSTEN_ALGORITHM_UNKNOWN_ERROR.getHttpStatus(),ExceptionEnum.SYSTEN_ALGORITHM_UNKNOWN_ERROR.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_UNKNOWN_ERROR.getMsgdesc());
                }

                if (MD5Password.equals(tbUsers.getUserPassword())){
//                        3.返回一些个人信息回去
                    String token = tokenService.getToken(tbUsers);
                    tbUsers.setUserToken(token);
//                        4.更新用户对应的token
                    usersServiceInterface.updateByPrimaryKey(tbUsers);
                    TbUsersPersonInfo personInfo = new TbUsersPersonInfo();
                    personInfo.setUserId(tbUsers.getUserId());
                    personInfo.setUserName(tbUsers.getUserName());
                    personInfo.setHeadImage(tbUsers.getHeadImage());
                    personInfo.setUserTelephone(tbUsers.getUserTelephone());
                    personInfo.setUserToken(token);
                    jsonDTO.setJsonDTO(true, ExceptionEnum.LOGIN_USER_SUCCESS.getMsgcode(), ExceptionEnum.LOGIN_USER_SUCCESS.getMsgdesc(), personInfo);
                }
                else {
                    jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_PASSWORD_ERROR.getMsgcode(),ExceptionEnum.LOGIN_PASSWORD_ERROR.getMsgdesc(),new ArrayList<>());
                }

            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgcode(),ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgdesc(),new ArrayList<>());
            }

        }else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgcode(),ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


    /**
    * @description: 获取公钥
    *
    * @return:
    **/
    @RequestMapping("/getpublickey")
    public JsonDTO getpublickey(){
        JsonDTO jsonDTO = new JsonDTO();
        PublicKey publicKey = null;
        String publicKeystr = null;
        try {
            publicKey = WHEncryptTools.getPemPublicKey("rsa_public_key.pem");
            publicKeystr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        }
        catch (IOException e){
            throw new CustomException(ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getHttpStatus(),ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getMsgcode(),ExceptionEnum.SYSTEN_NOT_FOUNDFILE.getMsgdesc());
        }
        catch (NoSuchAlgorithmException e){
            throw new CustomException(ExceptionEnum.SYSTEN_NO_ALGORITHM.getHttpStatus(),ExceptionEnum.SYSTEN_NO_ALGORITHM.getMsgcode(),ExceptionEnum.SYSTEN_NO_ALGORITHM.getMsgdesc());
        }
        catch (InvalidKeySpecException e){
            throw new CustomException(ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getHttpStatus(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgdesc());
        }
        HashMap hashMap = new HashMap();
        hashMap.put("publickey",publicKeystr);
        jsonDTO.setJsonDTO(true,ExceptionEnum.SYSTEN_GET_KEY_SUCCESS.getMsgcode(),ExceptionEnum.SYSTEN_GET_KEY_SUCCESS.getMsgdesc(),hashMap);
        return jsonDTO;
    }

    /**
     * @description: 注册用户信息
     *
     * @return:
     **/
    @RequestMapping("/register")
    public JsonDTO registerUser(@RequestParam(value = "username") String username,@RequestParam(value = "telephone") String telephone,@RequestParam(value = "password") String password,@RequestParam(value = "repeatpassword") String repeatpassword) throws CustomException{
        JsonDTO jsonDTO = new JsonDTO();
        // 1.判断用户名是否合规
        if (ValidatorFormatCheckTools.isUsername(username)){
            if (ValidatorFormatCheckTools.isPhoneLegal(telephone)){
//                需要解密两个密码才能进行比较

                String DescryPassword = null;
                String DescryREPassword = null;
                try {

                    byte[] passwordbyteArray = Base64.getDecoder().decode(password.getBytes());
                    byte[] RepasswordbyteArray = Base64.getDecoder().decode(repeatpassword.getBytes());

                    PrivateKey privateKey = WHEncryptTools.getPemPrivateKey("rsa_private_key.pem", "RSA");
                    DescryPassword = WHEncryptTools.RSADecrypt(Hex.encodeHexString(passwordbyteArray), privateKey);
                    DescryREPassword = WHEncryptTools.RSADecrypt(Hex.encodeHexString(RepasswordbyteArray), privateKey);
                }
                catch (Exception e){
                    jsonDTO.setJsonDTO(false,ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_KEY_INVALID.getMsgdesc(),new ArrayList<>());
                }
                if (DescryPassword.equals(DescryREPassword)){

                    if (usersServiceInterface.selectByTelephone(telephone).size() > 0){
                        jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_TELEPHONE_HASEXISTED.getMsgcode(),ExceptionEnum.LOGIN_TELEPHONE_HASEXISTED.getMsgdesc(),new ArrayList<>());
                    }
                    else {

                        String md5password = null;
                        try {
                            md5password = WHEncryptTools.MD5Encode(DescryPassword, "utf-8");
                        }catch (Exception e)
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.SYSTEN_ALGORITHM_UNKNOWN_ERROR.getMsgcode(),ExceptionEnum.SYSTEN_ALGORITHM_UNKNOWN_ERROR.getMsgdesc(),new ArrayList<>());
                        }
                            TbRoles tbRoles = tbrolesServiceInterface.selectByRoleLeve("0");
                            TbUsers tbUsers = new TbUsers();
                            tbUsers.setUserName(username);
                            tbUsers.setUserPassword(md5password);
                            tbUsers.setUserTelephone(telephone);
                            tbUsers.setUserRole(tbRoles.getRoleName());
                            String token = tokenService.getToken(tbUsers);
                            tbUsers.setUserToken(token);
//                            把用户插入到数据库中
                            if (usersServiceInterface.insert(tbUsers) > 0) {
//                                返回一些个人信息回去
                                TbUsersPersonInfo personInfo = new TbUsersPersonInfo();
                                TbUsers tbInfoUser = usersServiceInterface.selectByPrimaryKey(tbUsers.getUserId());
                                personInfo.setUserId(tbInfoUser.getUserId());
                                personInfo.setUserName(tbInfoUser.getUserName());
                                personInfo.setHeadImage(tbInfoUser.getHeadImage());
                                personInfo.setUserTelephone(tbInfoUser.getUserTelephone());
                                personInfo.setUserToken(tbInfoUser.getUserToken());
                                jsonDTO.setJsonDTO(true, ExceptionEnum.REGISTER_USER_SUCCESS.getMsgcode(), ExceptionEnum.REGISTER_USER_SUCCESS.getMsgdesc(), personInfo);
                            }
                    }

                }


                else {
                    jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_PASSWORD_UNFIT.getMsgcode(),ExceptionEnum.LOGIN_PASSWORD_UNFIT.getMsgdesc(),new ArrayList<>());
                }
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_TELEPHONE_NOTSUPOORT.getMsgcode(),ExceptionEnum.LOGIN_TELEPHONE_NOTSUPOORT.getMsgdesc(),new ArrayList<>());
            }
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_USERNAME_UNSOPPORT.getMsgcode(),ExceptionEnum.LOGIN_USERNAME_UNSOPPORT.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


    /**
     * @description: 检验前端提交的验证码是否正确,如果是正确的则返回结果到前端,如果不是则返回一些异常的代码过去
     *
     * @return:
     **/
    @RequestMapping("/verificode")
    public JsonDTO getVerificode(@RequestParam(value = "telephone") String telephone, @RequestParam(value = "code") String code) throws CustomException {
        JsonDTO jsonDTO = new JsonDTO();
//        首先判断是不是中国的区号,如果是再走下一步,如果不是则跳过直接返回错误回去
        if (ValidatorFormatCheckTools.isChinaPhoneLegal(telephone)){
//            检查手机号是否已经在数据库中
            if (usersServiceInterface.selectByTelephone(telephone).size()> 0){
                jsonDTO.setJsonDTO(false, ExceptionEnum.LOGIN_TELEPHONE_HASEXISTED.getMsgcode(),ExceptionEnum.LOGIN_TELEPHONE_HASEXISTED.getMsgdesc(),new ArrayList<>());
                return jsonDTO;
            }
            else {

                String result = requestData(telephone,code);
//           验证返回的结果是不是正确的,如果是正确的返回前端的公钥出去与结果
                if (JSON.parseObject(result).get("status").toString().equals("200"))
                {
//               获取公钥
                    String publickKeyStr = null;
                    try {
                        PublicKey publicKey = WHEncryptTools.getPemPublicKey("rsa_public_key.pem");
                        publickKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                    HashMap data = new HashMap();
                    data.put("publickey",publickKeyStr);
                    jsonDTO.setJsonDTO(true,ExceptionEnum.MOB_SUCCESS.getMsgcode(),ExceptionEnum.MOB_SUCCESS.getMsgdesc(),data);
                }
                else {
//               返回自定义的一些异常结果
                    switch (JSON.parseObject(result).get("status").toString()){
                        case "405":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_APPKEY_NULL.getMsgcode(),ExceptionEnum.MOB_APPKEY_NULL.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "406":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_APPKEY_INVALID.getMsgcode(),ExceptionEnum.MOB_APPKEY_INVALID.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "456":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_TELEPHONE_NULL.getMsgcode(),ExceptionEnum.MOB_TELEPHONE_NULL.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "457":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgcode(),ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "466":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_VERIFI_CODENULL.getMsgcode(),ExceptionEnum.MOB_VERIFI_CODENULL.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "467":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_VERIFI_REQUEST_FREQUENTLY.getMsgcode(),ExceptionEnum.MOB_VERIFI_REQUEST_FREQUENTLY.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "468":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_VERIFI_CODEERROR.getMsgcode(),ExceptionEnum.MOB_VERIFI_CODEERROR.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        case "474":
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_VERIFI_SERVERERROR.getMsgcode(),ExceptionEnum.MOB_VERIFI_SERVERERROR.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                        default:
                        {
                            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_UNKNOWN_ERROR.getMsgcode(),ExceptionEnum.MOB_UNKNOWN_ERROR.getMsgdesc(),new ArrayList<>());
                            return jsonDTO;
                        }
                    }
                }
            }
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_TELEPHONE_NOTSUPOORT.getMsgcode(),ExceptionEnum.LOGIN_TELEPHONE_NOTSUPOORT.getMsgdesc(),new ArrayList<>());
        }

        return jsonDTO;
    }

    //    去SMSSDK服务器验证验证码是否正确

    public  static String requestData(String telephone ,String code){

        //            拿到手机号跟提交的验证码去SMSSDK服务器请求验证结果是否正确
        String address = "https://webapi.sms.mob.com/sms/verify";
//            appkey写成固定的同时国内的区号都固定为86,上传git的时候key去掉
        String params = "appkey="+"148b4c395d8c0"+"&"+"phone="+telephone+"&zone=86&&code="+code;
        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
                public X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(X509Certificate[] certs, String authType){}
                public void checkServerTrusted(X509Certificate[] certs, String authType){}
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params
            if (params!=null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            //get result
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String result = parsRtn(conn.getInputStream());
                return result;
            } else {
                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

    /**
     * @description: 获取返回数据
     *
     * @return:
     **/
    private static String parsRtn(InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        boolean first = true;
        while ((line = reader.readLine()) != null) {
            if(first){
                first = false;
            }else{
                buffer.append("\n");
            }
            buffer.append(line);
        }
        return buffer.toString();
    }


    @SuperAuthorityToken
    @RequestMapping("/alluser")
    public JsonDTO getAllUser(@RequestParam(value = "pageno") int pageno,@RequestParam(value = "pagesize") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageno,pagesize);
        PageInfo<TbUsers> pageInfo = new PageInfo<>(usersServiceInterface.selectAll());
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }
}
