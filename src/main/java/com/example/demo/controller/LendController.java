package com.example.demo.controller;


import com.example.demo.common.Result;
import com.example.demo.service.ILendService;
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
@RequestMapping("/lend")
public class LendController {
    @Resource
    private ILendService lendService;

    /**
     * 根据名字和书名查询借阅情况
     */
    @PostMapping("/getLendInfo")
    public Result getLendInfos(@RequestBody Map<String,String> params){
        String username = params.get("username");
        String bookname = params.get("bookname");
        return lendService.getLendInfo(username,bookname);
    }

    /**
     *所有借阅信息
     */
    @GetMapping("/getAll")
    public Result getAllInfo(@RequestParam int page, @RequestParam int size) {
        return lendService.getAllInfo(page, size);
    }

}
