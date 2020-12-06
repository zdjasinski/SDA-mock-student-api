package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    public Student(String name, String email, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }
}
