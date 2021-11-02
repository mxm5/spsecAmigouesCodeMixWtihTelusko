package com.example.spsecamigouescode.Student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    List<Student> STUDENTS = Arrays.asList(
            new Student(1, "james "),
            new Student(2, "sara "),
            new Student(3, "jill ")
    );

    @GetMapping("{studentId}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public Student getStudent(@PathVariable("studentId") Integer studentId ) {
        return STUDENTS.stream().filter(
                student -> studentId.equals(student.getStudentId())
        ).findFirst().orElseThrow(
                () -> new IllegalStateException(" user not found")
        );
    }
}
