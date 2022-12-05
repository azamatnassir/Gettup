package com.example.gettup.repository;

import com.example.gettup.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findLessonByTitle(String title);
}
