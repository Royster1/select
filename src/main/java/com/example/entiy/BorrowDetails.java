package com.example.entiy;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowDetails {
    int id;
    String book_title;
    String user_name;
    Date time;
}
