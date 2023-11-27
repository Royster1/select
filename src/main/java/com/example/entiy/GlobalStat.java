package com.example.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;

// 全局统计
@Data
@AllArgsConstructor // 构造函数
public class GlobalStat {
    int userCount;
    int bookCount;
    int borrowCount;
}
