package com.example.entiy;


import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    int id;
    int bid;
    int sid;
    Date data;
}
