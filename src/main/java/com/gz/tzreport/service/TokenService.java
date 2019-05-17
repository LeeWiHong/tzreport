package com.gz.tzreport.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gz.tzreport.pojo.TbUsers;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
//    private static final long EXPIRE_TIME = 60 * 60 * 24 * 30;
    public String getToken(TbUsers tbUsers) {

        String token = "";
        token = JWT.create()
                .withAudience(tbUsers.getUserTelephone().toString())
                .sign(Algorithm.HMAC256(tbUsers.getUserPassword()));
        return token;
    }
}
