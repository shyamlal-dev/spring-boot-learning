package com.sms.studentmanagementsystem.service;

import org.springframework.stereotype.Service;
import com.sms.studentmanagementsystem.repository.StudentRepository;
import com.sms.studentmanagementsystem.entity.Student;
import com.sms.studentmanagementsystem.exception.StudentNotFoundException;
import com.sms.studentmanagementsystem.dto.CreateStudentRequest;
import com.sms.studentmanagementsystem.exception.DuplicateEmailException;
import com.sms.studentmanagementsystem.dto.StudentResponse;
import com.sms.studentmanagementsystem.dto.UpdateStudentRequest;
import java.util.List;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;

    /**@Autowired (No need here, because it is default in constructor              injection in case of single constructor)*/
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse createStudent(CreateStudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists!");
        }
        
        Student student = new Student();
        
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setCourse(request.getCourse());
        
        Student savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent);
    }

    public List<StudentResponse> getStudents() {
        return studentRepository.findAll()
               .stream()
               .map(this :: mapToResponse)
               .toList(); 
        
    }

    public StudentResponse getStudentById(Long id) {
        Student student =  findStudentById(id);
        return mapToResponse(student);
    }

    public StudentResponse updateStudent(Long id, UpdateStudentRequest request) {
        Student existingStudent = findStudentById(id);
        if(studentRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateEmailException("Email already exists!");
        }
        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setCourse(request.getCourse());

        Student updatedStudent = studentRepository.save(existingStudent);

        return mapToResponse(updatedStudent);
    }

    public void deleteStudent(Long id) {
        findStudentById(id);
        studentRepository.deleteById(id);
    }

    private StudentResponse mapToResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setCourse(student.getCourse());

        return studentResponse;
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
}