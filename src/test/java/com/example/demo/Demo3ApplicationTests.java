package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.Lend;
import com.example.demo.mapper.LendMapper;
import com.example.demo.service.IBookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Demo3ApplicationTests {

    @Resource
    private LendMapper lendMapper;
    @Resource
    private IBookService bookService;
    @Test
    void contextLoads() {
//        bookService.returnBooks("许三观卖血记");
        String bookname="许三观卖血记";
        String username="雷明";
//        LambdaQueryWrapper<Lend> queryWrapper1=new LambdaQueryWrapper<Lend>();
        lendMapper.selectInfo(bookname,username);
    }

}
