package com.ocean.cloudcommon.utils;

import com.ocean.cloudcommon.constants.CommonConstants;
import com.ocean.cloudcommon.dto.UserTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @author bootdo 1992lcg@163.com
 * @version V1.0
 */
public class JwtUtils {
    public static String generateToken(UserTokenDto userToken, int expire) throws Exception {
        String token = Jwts.builder()
                .setSubject(userToken.getUsername())
                .claim(CommonConstants.CONTEXT_USER_ID, userToken.getUserId())
                .claim(CommonConstants.CONTEXT_NAME, userToken.getName())
                .claim(CommonConstants.RENEWAL_TIME,new Date(System.currentTimeMillis()+expire/2))
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY)
                .compact();
        return token;
    }


    public static UserTokenDto getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(token)
                .getBody();
        return new UserTokenDto(claims.getSubject(), claims.get(CommonConstants.CONTEXT_USER_ID).toString(),claims.get(CommonConstants.CONTEXT_NAME).toString());
    }

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        System.out.println(dateFormat.format(new Date(System.currentTimeMillis()+(60*60*1000)/2)));
    }

}
