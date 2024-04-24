package com.example.demo.controller;


import com.example.demo.DTO.registerDto;
import com.example.demo.common.Result;
import com.example.demo.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     *注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody registerDto registerDto){
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        String email = registerDto.getEmail();
        String code = registerDto.getCode();
        return  userService.regist(username,password,email,code);

    }

    /**
     * 发送验证码
     */
    @GetMapping("/code")
    public Result sendCode(@Param("email") String email){
        Boolean res = userService.sendCode(email);
        if (!res){
            return Result.fail("发送失败");
        }

        return Result.success("发送成功");
    }

    /**
     *登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> params){
        String email = params.get("email");
        String password = params.get("password");
        return  userService.login(email,password);
    }

}
