package com.example.demo.api;

import com.example.demo.dto.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(StudentController.URL)
public class StudentController {

    protected static final String URL = "/api/v1/student";
    private static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(1L, "marek@prz.edu.pl", "Marek Kowalski", LocalDateTime.now(), null));
    }

    @GetMapping()
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(StudentController.students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") Long id) {
        return getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping()
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        Long id = getNextId();
        student.setId(id);
        student.setCreatedDate(LocalDateTime.now());
        student.setUpdateDate(null);

        StudentController.students.add(student);

        return ResponseEntity.ok(student);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return getById(id).map(studentEntity -> {
            studentEntity.setEmail(student.getEmail());
            studentEntity.setName(student.getName());
            studentEntity.setUpdateDate(LocalDateTime.now());

            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        boolean removed = students.removeIf(post -> post.getId().equals(id));

        return ResponseEntity.ok(removed);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        students.clear();

        return ResponseEntity.ok().build();
    }

    private Optional<Student> getById(Long id) {
        return StudentController.students.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    private Long getNextId() {
        int max = students
                .stream()
                .mapToInt(v -> Math.toIntExact(v.getId()))
                .max().orElse(0);

        return (long) ++max;
    }
}
