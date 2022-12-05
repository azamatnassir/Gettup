package com.example.gettup.service;

import com.example.gettup.entity.Course;
import com.example.gettup.entity.Student;
import com.example.gettup.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public void save(Student student){
        studentRepository.save(student);
    }

    public Student getByUserID(Long id){
        return studentRepository.findStudentByUserId(id).orElse(null);
    }

    public void enroll(Course course, Long id){
        Student student = getByUserID(id);
        student.getCourses().add(course);
        studentRepository.save(student);

        course.getStudents().add(student);
        courseRepository.save(course);
    }

}
