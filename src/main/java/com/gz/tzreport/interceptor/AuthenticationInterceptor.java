package com.gz.tzreport.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gz.tzreport.annotation.PassToken;
import com.gz.tzreport.annotation.UserLoginToken;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.UsersServiceInterface;
import com.gz.tzreport.uitls.CustomException;
import com.gz.tzreport.uitls.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UsersServiceInterface usersServiceInterface;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws CustomException {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new CustomException(ExceptionEnum.LOGIN_TOKEN_NULL.getHttpStatus(),ExceptionEnum.LOGIN_TOKEN_NULL.getMsgcode(),ExceptionEnum.LOGIN_TOKEN_NULL.getMsgdesc());
                }
                // 获取 token 中的 user id
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
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tbUsers.getUserPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new CustomException(ExceptionEnum.LOGIN_TOKEN_INVALID.getHttpStatus(),ExceptionEnum.LOGIN_TOKEN_INVALID.getMsgcode(),ExceptionEnum.LOGIN_TOKEN_INVALID.getMsgdesc());
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
