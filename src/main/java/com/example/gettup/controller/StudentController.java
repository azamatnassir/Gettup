package com.example.gettup.controller;

import com.example.gettup.entity.Course;
import com.example.gettup.entity.Student;
import com.example.gettup.entity.User;
import com.example.gettup.service.CourseService;
import com.example.gettup.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/public/courses")
    public String courses(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Student student = studentService.getByUserID(user.getId());

        List<Course> all = courseService.getAll();
        List<Course> courses = new ArrayList<>(all);
        if (student.getCourses() != null){
            List<Course> enrolled = student.getCourses();
            courses.removeAll(enrolled);
        }

        model.addAttribute("public", "active");
        model.addAttribute("courses", courses);
        return "student/courses";
    }

    @GetMapping("student/support")
    public String supportAuth(Model model) {
        model.addAttribute("support", "active");
        return "general/support";
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam("courseId") Long id,
                                Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = courseService.getByID(id);
        studentService.enroll(course, user.getId());
        model.addAttribute("home", "active");
        return "redirect:/home";
    }
}
