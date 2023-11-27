package com.example.mapper;


import com.example.entiy.Book;
import com.example.entiy.Borrow;
import com.example.entiy.BorrowDetails;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    // 查询数据库图书信息
    @Select("select * from book")
    List<Book> allBook();

    // 通过书籍bid插叙图书信息
    @Select("select * from book where bid = #{bid}")
    Book getBookById(int bid);

    // 删除书籍
    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    // 添加书籍
    @Insert("insert into book(title, `desc`, price) values(#{title}, #{desc}, #{price})")
    void addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") double price);

    @Insert("insert into borrow(bid, sid, `time`) values(#{bid}, #{sid}, NOW())")
    void addBorrow(@Param("bid") int bid, @Param("sid") int sid);

    // 获取所有借阅信息, 用户借阅后, 用户系统的借阅系统页面的那本书应该也要删掉
    @Select("select * from borrow")
    List<Borrow> BorrowList(); // 用户借阅列表中没用这本书才能给用户借

    // 这个学生借阅了什么书籍
    @Select("select * from borrow where sid = #{sid}")
    List<Borrow> borrowListBySid(int sid);

    // 规划图书
    @Delete("delete from borrow where bid = #{bid} and sid = #{sid}")
    void deleteBorrow(@Param("bid") int bid, @Param("sid") int sid);

    // 联查语句, 查询被借阅书籍状态和借阅人是谁 写映射
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "title", property = "book_title"),
            @Result(column = "name", property = "user_name"),
            @Result(column = "time", property = "time"),
    })
    @Select("select * from borrow left join book on book.bid = borrow.bid" +
            " left join student on borrow.sid = student.sid")
    List<BorrowDetails> borrowDetails();


    // 书籍数量 和 借阅数量
    @Select("select count(*) from book")
    int getBookCount();
    @Select("select count(*) from borrow")
    int getBorrowCount();
}
