package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.Utils.HashUtil;
import com.example.demo.Utils.JWTUtils;
import com.example.demo.Utils.MailUtils;
import com.example.demo.Utils.RedisConstants;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.example.demo.Utils.CommonUtils.checkEmail;
import static com.example.demo.Utils.CommonUtils.getRandomCode;
import static com.example.demo.Utils.RedisConstants.LOGIN_USER_KEY;
import static com.example.demo.Utils.RedisConstants.LOGIN_USER_TTL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册
     */
    @Override
    public Result regist(String username, String password, String email,String code) {
        /**
         * 根据邮箱判断是否注册过
         */
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        User user = getOne(queryWrapper);
        if (user!=null){
            return Result.fail("该用户注册过了");
        }
        /**
         * 查看验证码是否正确
         */
        String TCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + email);
        if (!code.equals(TCode)){
            return Result.fail("邮箱输入错误！");
        }
        User user1 = new User().setEmail(email)
                .setPassword(HashUtil.hashPassword(password))
                .setUsername(username);
        int save = userMapper.insert(user1);
        if (save!=0){
            return Result.success("注册成功！");
        }else {
            return Result.fail("注册失败！");
        }
    }



    /**
     * 发送验证码
     */
    @Override
    public Boolean sendCode(String email) {
        /**
         * 验证邮箱是否合法
         */
        if (!checkEmail(email)) {
            return false;
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        boolean res = MailUtils.sendMail(email, code, "验证码：有效期三分钟！");
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY+email,code,RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        return res;

    }

    /**
     * 登录
     */
    @Override
    public Result login(String email, String password) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail,email);
        User user = getOne(userLambdaQueryWrapper);
        if (user==null){
            return Result.fail("当前用户不存在！");
        }
        /**
         * 对比密码是否正确
         */
        if(!HashUtil.verifyPassword(password,user.getPassword())){

            return Result.fail("密码错误！");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("email",email);
        map.put("username",user.getUsername());
        String token = JWTUtils.getToken(map);
        //把token存入Redis。有效期为30分钟
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+email,token,LOGIN_USER_TTL,TimeUnit.MINUTES);
        return Result.success(token);
    }
}
