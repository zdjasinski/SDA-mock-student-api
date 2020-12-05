package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class Student {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
