package com.example.demo.api;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(StudentController.URL)
public class StudentController {

    final StudentRepository studentRepository;

    protected static final String URL = "/api/v1/student";

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") Long id) {
        return getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping()
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        Student newStudent = new Student(
            student.getName(),
            student.getEmail(),
            LocalDateTime.now(),
            null
        );

        Student studentEntity = studentRepository.save(newStudent);

        return ResponseEntity.ok(studentEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return getById(id).map(studentEntity -> {
            studentEntity.setEmail(student.getEmail());
            studentEntity.setName(student.getName());
            studentEntity.setUpdateDate(LocalDateTime.now());

            studentRepository.save(studentEntity);

            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        studentRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    private Optional<Student> getById(Long id) {
        return studentRepository.findById(id);
    }
}
