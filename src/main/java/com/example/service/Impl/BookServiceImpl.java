package com.example.service.Impl;

import com.example.entiy.Book;
import com.example.entiy.Borrow;
import com.example.entiy.BorrowDetails;
import com.example.mapper.BookMapper;
import com.example.mapper.UserMapper;
import com.example.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper mapper;

    @Resource
    UserMapper userMapper; // service 只跟 mapper 打交道, 所有的controller 只跟 service打交道

    @Override
    public List<Book> getAllBook() {
        return mapper.allBook();
    }

    // 获取所有书籍, 过滤掉已经给借阅
    @Override
    public List<Book> getAllBookWithOutBorrow() {
        List<Book> books = mapper.allBook();
        List<Integer> borrows = mapper.BorrowList()
                .stream()
                .map(Borrow::getBid)
                .collect(Collectors.toList());
        return books.stream().filter(book -> !borrows.contains(book.getBid())).collect(Collectors.toList());
    }

    // 获取用户已经借阅的书籍
    public List<Book> getAllBorrowBookByoId(int id) {
        // 通过id查询该学生的sid
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return Collections.emptyList(); // 返回空列表
        return mapper.borrowListBySid(sid) // 拿到学生的借阅信息
                .stream()
                .map(borrow -> {
                    return mapper.getBookById(borrow.getBid());
                })
                .collect(Collectors.toList());
    }


    // 删除书籍
    @Override
    public void deleteBook(int bid) {
        mapper.deleteBook(bid);
    }


    // bookService -> bookMapper -> Impl -> adminController
    // 添加书籍
    @Override
    public void addBook(String title, String desc, double price) {
        mapper.addBook(title, desc, price);
    }

    @Override
    public void borrowBook(int bid, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.addBorrow(bid, sid);
    }

    @Override
    public void returnBook(int bid, int id) {
        Integer sid = userMapper.getSidById(id);
        if (sid == null) return;
        mapper.deleteBorrow(bid, sid);
    }

    @Override
    public List<BorrowDetails> getBorrowDetails() {
        return mapper.borrowDetails();
    }
}
