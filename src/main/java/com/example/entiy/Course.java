package com.example.entiy;

import lombok.Data;

@Data
public class Course {
    String course_id;
    String course_name;
    String teacher;
    int point;
    String location;
    int limited;
    int SelectNum;
}
