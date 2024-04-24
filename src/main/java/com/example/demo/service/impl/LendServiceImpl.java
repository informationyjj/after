package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.DTO.PageInfo;
import com.example.demo.Utils.JWTUtils;
import com.example.demo.common.Result;
import com.example.demo.entity.Lend;
import com.example.demo.entity.User;
import com.example.demo.mapper.LendMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ILendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements ILendService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private LendMapper lendMapper;
    /**
     * 查询
     */
    @Override
    public Result<List<Lend>> getLendInfo(String username, String bookname) {
//        String authorization = httpServletRequest.getHeader("Authorization");
//        if (authorization==null){
//            return Result.fail("请先登录！");
//        }
//        authorization = authorization.replace("Bearer ", "");
        //当前登录用户
//        String usernameNow = JWTUtils.DecodeToken(authorization).getClaim("username").asString();

//        //比对当前登录用户查询输入姓名是否是自己
//        if (!usernameNow.equals(username)){
//            return Result.fail("您没有权限查看！");
//        }
        List<Lend> lends = lendMapper.getLends(username, bookname);
        return Result.success(lends);
    }

    /**
     *默认全部展示
     */
    @Override
    public Result<PageInfo<Lend>> getAllInfo(int page, int size) {
        // 计算起始索引
        int startIndex = (page - 1) * size;

        // 查询当前页数据
        List<Lend> currentPageData = lendMapper.getAllInfo(startIndex, size);

        // 查询总记录数
        int totalRecords = lendMapper.getTotalRecords();

        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalRecords / size);

        // 构造分页信息对象
        PageInfo<Lend> pageInfo = new PageInfo<>();
        pageInfo.setCurrentPage(page);
        pageInfo.setPageSize(size);
        pageInfo.setTotalRecords(totalRecords);
        pageInfo.setTotalPages(totalPages);
        pageInfo.setData(currentPageData);

        return Result.success(pageInfo);
    }

}
