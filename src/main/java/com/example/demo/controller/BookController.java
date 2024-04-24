package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.IBookService;
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
@RequestMapping("/book")
public class BookController {
    @Resource
    private IBookService bookService;

    /**
     * 还书
     * @param
     * @return
     */
    @PostMapping("/return")
    public Result returnBook(@RequestBody Map<String,String> params){
        final String bookname = params.get("bookname");
        return bookService.returnBooks(bookname);
    }
    @PostMapping("/borrow")
    public Result BorrowBook(@RequestBody Map<String,String> params){
        String bookname = params.get("bookname");
        return bookService.borrowBook(bookname);
    }

}
