package com.example.gettup.service;

import com.example.gettup.entity.Course;
import com.example.gettup.entity.Creator;
import com.example.gettup.entity.FileModel;
import com.example.gettup.entity.Lesson;
import com.example.gettup.repository.CourseRepository;
import com.example.gettup.repository.CreatorRepository;
import com.example.gettup.repository.FileRepository;
import com.example.gettup.repository.LessonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CreatorService {

    private final CreatorRepository creatorRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final FileRepository fileRepository;

    public void save(Creator creator){
        creatorRepository.save(creator);
    }

    public Creator getByUserID(Long id){
        return creatorRepository.findByUserId(id).orElse(null);
    }

    public String addCourse(Long id, String name, String description, MultipartFile file) throws IOException {
        if (courseRepository.findCourseByName(name).isPresent()){
            return "exists";
        }
        Course course = new Course(name, description, file.getBytes());
        courseRepository.save(course);
        Creator creator = getByUserID(id);
        creator.getCourses().add(course);
        creatorRepository.save(creator);
        return "success";
    }

    public String addLesson(Long user_id, Long course_id, String title, String description, MultipartFile[] materials) throws IOException {
        if (lessonRepository.findLessonByTitle(title).isPresent()){
            return "exists";
        }

        Course course = courseRepository.findCourseById(course_id).orElse(null);
        Lesson lesson = new Lesson(title, description);
        for (MultipartFile file:materials) {
            FileModel fileModel = new FileModel(file.getName(), file.getContentType(), file.getBytes());
            fileRepository.save(fileModel);

            lesson.getFiles().add(fileModel);
            lessonRepository.save(lesson);
        }
        Objects.requireNonNull(course).getLessons().add(lesson);
        courseRepository.save(course);

        Creator creator = getByUserID(user_id);
        creator.getCourses().add(course);
        creatorRepository.save(creator);
        return "success";
    }
}
