package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    private int currentPage;
    private int pageSize;
    private int totalRecords;
    private int totalPages;
    private List<T> data;

    // 省略构造方法、getter 和 setter 方法
}

