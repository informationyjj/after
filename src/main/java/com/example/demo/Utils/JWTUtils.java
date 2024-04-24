package com.example.demo.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


@Component
public class JWTUtils {

    private static String salt="book_book";

    /**
     * 生成token,默认有效时间是30分钟
     * @param map  //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,30);
        Date expiresDate = nowTime.getTime();

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        builder.withIssuedAt(new Date())
                .withExpiresAt(expiresDate);
        return builder.sign(Algorithm.HMAC256(salt));
    }
    /**
     * 验证token
     * @param token
     * @return
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(salt)).build().verify(token);  // 如果验证通过，则不会把报错，否则会报错
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT DecodeToken(String token){
        return JWT.require(Algorithm.HMAC256(salt)).build().verify(token);
    }
}
