package com.example.demo.mapper;

import cn.hutool.core.date.DateTime;
import com.auth0.jwt.interfaces.Claim;
import com.example.demo.entity.Lend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
public interface LendMapper extends BaseMapper<Lend> {

    @Select("select * from lend where username=#{username} and bookname=#{bookname}")
   List<Lend> getLends(@Param("username") String username, @Param("bookname") String bookname);

    @Select("select * from lend")
    List<Lend> getAllInfo();

//    @Update("update lend set backDate=#{backDate} where bookname=#{bookname} and username=#{username}")
    @Update("update lend set backDate=#{backDate} where username=#{username} and bookname=#{bookname}")
    Boolean updateInfo(@Param("username") String username,@Param("bookname") String bookname,@Param("backDate") DateTime backDate);

    @Insert("insert into lend(bookname,username,lendDate) values (#{bookname},#{username},#{lendDate})")
    Boolean InsertBook(@Param("username") String username, @Param("bookname") String bookname, @Param("lendDate") DateTime lendDate);

    @Select("select * from lend where bookname=#{bookname} and username=#{username}")
    Lend selectInfo(@Param("bookname") String bookname,@Param("username") String username);

    @Select("SELECT * FROM lend ORDER BY id LIMIT #{startIndex}, #{size} ")
    List<Lend> getAllInfo(@Param("startIndex") int startIndex,@Param("size")int size);

    @Select("SELECT COUNT(*) FROM lend")
    int getTotalRecords();
}
