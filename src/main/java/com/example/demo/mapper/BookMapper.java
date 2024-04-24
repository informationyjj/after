package com.example.demo.mapper;

import com.example.demo.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
public interface BookMapper extends BaseMapper<Book> {

    @Update("update book set booknum=booknum+1 where bookname=#{bookname}")
    Boolean returnBook(@Param("bookname") String bookname);

    @Update("update book set booknum=booknum-1 where bookname=#{bookname}")
    Boolean borrowBook(String bookname);
}
