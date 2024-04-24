package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
public interface IUserService extends IService<User> {
    Result regist(String username, String password, String email,String code);

    Boolean sendCode(String email);

    Result login(String email,String password);
}
