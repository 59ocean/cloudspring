package com.ocean.cloudgateway;


import com.ocean.cloudgateway.constants.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author bootdo 1992lcg@163.com
 * @version V1.0
 */
public class JwtUtils {
    public static String generateToken(UserToken userToken, int expire) throws Exception {
        String token = Jwts.builder()
                .setSubject(userToken.getUsername())
                .claim(CommonConstants.CONTEXT_USER_ID, userToken.getUserId())
                .claim(CommonConstants.CONTEXT_NAME, userToken.getName())
                .claim(CommonConstants.RENEWAL_TIME,new Date(System.currentTimeMillis()+CommonConstants.ACCESS_TOKEN_VALIDITY_SECONDS*expire/2))
                .setExpiration(new Date(System.currentTimeMillis()+CommonConstants.ACCESS_TOKEN_VALIDITY_SECONDS*expire))
                .signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY)
                .compact();
        return token;
    }


    public static UserToken getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(token)
                .getBody();
        return new UserToken(claims.getSubject(), claims.get(CommonConstants.CONTEXT_USER_ID).toString(),claims.get(CommonConstants.CONTEXT_NAME).toString());
    }

    public static void main(String[] args) throws Exception {
        UserToken userToken = new UserToken("ocean","1","first");

      //  System.out.println();
        userToken = getInfoFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvY2VhbiIsImNvbnRleHRVc2VySWQiOiIxIiwiY29udGV4dE5hbWUiOiJmaXJzdCIsInJlbmV3YWxUaW1lIjoxNTQ4NjUzNjU2MTU2LCJleHAiOjE1NDg2NjI2NTZ9.VUfvSCXZrLtb43fh3U40whmUnbGgfK0YpAAUSHO4MH8");
       //System.out.println(generateToken(userToken,1000));
        System.out.println(userToken);
    }
}
