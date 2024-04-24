package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
public interface IBookService extends IService<Book> {

    Result returnBooks(String bookname);

    Result borrowBook(String bookname);

//    int returnBook(String bookname);
}
