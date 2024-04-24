package com.example.demo.service;

import com.example.demo.DTO.PageInfo;
import com.example.demo.common.Result;
import com.example.demo.entity.Lend;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjj
 * @since 2024-04-23
 */
public interface ILendService extends IService<Lend> {
    Result<List<Lend>> getLendInfo(String username, String bookname);
    Result<PageInfo<Lend>> getAllInfo(int page, int size);
}
