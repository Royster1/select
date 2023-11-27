package com.example.service;

import com.example.entiy.Book;
import com.example.entiy.BorrowDetails;

import java.util.List;

public interface BookService {
    List<Book> getAllBook();

    // 获取过滤掉已经给借阅了的所有书籍
    List<Book> getAllBookWithOutBorrow();

    // 用户借阅的书籍
    List<Book> getAllBorrowBookByoId(int id);


    void deleteBook(int bid);

    void addBook(String title, String desc, double price);

    void borrowBook(int bid, int id);

    // 归还图书
    void returnBook(int bid, int id);

    // 图书管理系统 图书借阅信息一览
    List<BorrowDetails> getBorrowDetails();
}
