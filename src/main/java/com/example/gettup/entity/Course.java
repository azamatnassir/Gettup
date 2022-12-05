package com.example.gettup.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_course")
@Setter
@Getter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    private byte[] avatar;

    @OneToMany(targetEntity = Lesson.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    public Course(String name, String description, byte[] avatar) {
        this.name = name;
        this.description = description;
        this.avatar = avatar;
    }

    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getAvatar());
    }
}
