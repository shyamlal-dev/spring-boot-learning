package com.sms.studentmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.sms.studentmanagementsystem.dto.CreateStudentRequest;
import com.sms.studentmanagementsystem.service.StudentService;
import com.sms.studentmanagementsystem.dto.StudentResponse;
import com.sms.studentmanagementsystem.dto.UpdateStudentRequest;
import java.util.List;


@RestController
public class StudentController {

    private final StudentService studentService;

    /**@Autowired (No need here, because it is default in constructor injection in case of single constructor)*/
    public StudentController(StudentService studentService) {
        this.studentService  = studentService;
    }
    
    @GetMapping("/")  
    public String home(){
        return "Student Management System is running";
    }        

    @PostMapping("/students")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        StudentResponse createdStudent = studentService.createStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/students")
    public List<StudentResponse> getStudents(){
        return studentService.getStudents();
    }

    @GetMapping("/students/{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/students/{id}")
    public StudentResponse updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
} 