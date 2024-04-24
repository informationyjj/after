package com.example.demo.service.impl;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.Utils.JWTUtils;
import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.example.demo.entity.Lend;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.LendMapper;
import com.example.demo.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author
 * @since 2024-04-23
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Resource
    private HttpServletRequest request;
    @Resource
    private LendMapper lendMapper;
    @Resource
    private BookMapper bookMapper;

    @Transactional
    @Override
    public Result returnBooks(String bookname) {
        String authorization = request.getHeader("Authorization");
        authorization = authorization.replace("Bearer ", "");
        String username = JWTUtils.DecodeToken(authorization).getClaim("username").asString();
//        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<Book>();
//        queryWrapper.eq(Book::getBookname, bookname);
//        Book book = getOne(queryWrapper);
//        String bookName = book.getBookName();
        LambdaQueryWrapper<Lend> queryWrapper1=new LambdaQueryWrapper<Lend>();
//        queryWrapper1.eq(Lend::getBookname,bookname)
//                .eq(Lend::getUsername,username);
        Lend lend = lendMapper.selectInfo(bookname,username);
        if (lend==null){
            return Result.fail("您未借此本书");
        }
        Boolean ret = bookMapper.returnBook(bookname);
        Boolean up = lendMapper.updateInfo(username, bookname,DateTime.now());
        if (ret && up){
            return Result.success("归还成功");
        }
        return Result.fail("归还失败");
    }

    @Transactional
    @Override
    public Result borrowBook(String bookname) {

        String authorization = request.getHeader("Authorization");
        authorization = authorization.replace("Bearer ", "");
        Claim username = JWTUtils.DecodeToken(authorization).getClaim("username");
        LambdaQueryWrapper<Book> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getBookname,bookname).select(Book::getBooknum);
        Book book = getOne(queryWrapper);
        Integer bookNum = book.getBooknum();
        List<Lend> lends = lendMapper.getLends(username.asString(), bookname);
        if (lends.size()!=0){
            return Result.fail("您已借阅此书，不可重复借阅！");
        }
        else if (bookNum==0){
            return Result.fail("图书库存已空");
        }
       Boolean borrow= bookMapper.borrowBook(bookname);
        Boolean insert = lendMapper.InsertBook(username.asString(),bookname, DateTime.now());
        if (!borrow && insert){
            return Result.fail("借阅失败");
        }
        return Result.success("借阅成功");
    }
}
