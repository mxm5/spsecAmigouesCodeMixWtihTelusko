package com.example.spsecamigouescode.Student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("manage")
public class StudentManagementApi {

    List<Student> STUDENTS = Arrays.asList(
            new Student(1, "james "),
            new Student(2, "sara "),
            new Student(3, "jill ")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(Student student) {
        System.out.println(student);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println(studentId);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, Student student) {
        System.out.println(" student: "+student +" id: "+ studentId);
    }
}
