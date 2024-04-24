package com.example.demo.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.Utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Slf4j
@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        //获取请求头中的令牌
        String token = request.getHeader("Authorization");

        try {
            String token1 = token.replace("Bearer ", "");
            String userName = JWTUtils.DecodeToken(token1).getClaim("username").asString();
            String email = JWTUtils.DecodeToken(token1).getClaim("email").asString();
            log.debug("用户的姓名是{}",userName);
            log.debug("用户的邮箱是{}",email);
            JWTUtils.verify(token1);

            //进行角色认证


            return  true;//放行请求
    /*
   常见异常
   SignatureVerificationException: 签名不一致异常
   TokenExpiredException: 令牌过期异常
   AlgorithmMismatchException: 算法不匹配异常
   */
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg","token过期");
        } catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg","token算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","token无效");
        }
        map.put("state",false);//设置状态
        //将map转为json jackjon
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
