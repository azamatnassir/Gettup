package com.example.gettup.service;

import com.example.gettup.entity.Course;
import com.example.gettup.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course getByID(Long id){
        return courseRepository.findById(id).orElse(null);
    }

    public List<Course> getAll(){
        return courseRepository.findAll();
    }
}
