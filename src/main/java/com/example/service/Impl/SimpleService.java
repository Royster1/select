package com.example.service.Impl;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SimpleService {
    // @PreAuthorize("hasAnyRole('admin')")
    @PreFilter("filterObject.equals('lbwnb')")       //filterObject代表集合中每个元素，只要满足条件的元素才会留下
    public void test(List<String> list){
        System.out.println("成功执行"+list);
    }


    /*@PostFilter("filterObject.equals('lbwnb')")
    public List<String> test(){
        List<String> list = new LinkedList<>();
        list.add("lbwnb");
        list.add("yyds");
        return list;
    }*/
}
