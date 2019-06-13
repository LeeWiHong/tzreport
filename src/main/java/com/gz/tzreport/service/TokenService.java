package com.gz.tzreport.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gz.tzreport.pojo.TbUsers;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("TokenService")
public class TokenService {
//    private static final long EXPIRE_TIME = 60 * 60 * 24 * 30;
    public String getToken(TbUsers tbUsers) {

        String token = "";
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("telephone",tbUsers.getUserTelephone());
        claims.put("state",tbUsers.getUserState());
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,tbUsers.getUserPassword());
        return builder.compact();
    }
}
