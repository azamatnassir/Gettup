package com.example.gettup.controller;

import com.example.gettup.entity.User;
import com.example.gettup.service.CreatorService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping("/students")
    public String courses(Model model) {
        model.addAttribute("students", "active");
        return "creator/students";
    }

    @GetMapping("creator/support")
    public String supportAuth(Model model) {
        model.addAttribute("support", "active");
        return "general/support";
    }

    @GetMapping("/create/course")
    public String addCourse(Model model) {
        model.addAttribute("home", "active");
        return "creator/createCourse";
    }

    @PostMapping("/create/lesson")
    public String lesson(@RequestParam("courseId") Long course_id,
                            Model model) {
        model.addAttribute("home", "active");
        model.addAttribute("course_id", course_id);
        return "creator/createLesson";
    }

    @PostMapping("/add/lesson")
    public String addLesson(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("materials") MultipartFile[] materials,
                            @RequestParam("courseId") Long course_id,
                            Model model, Authentication authentication) throws IOException {
        model.addAttribute("home", "active");
        User user = (User)authentication.getPrincipal();
        String res = creatorService.addLesson(user.getId(), course_id, title, description, materials);
        if (res.equals("exists")){
            model.addAttribute("message", "exists");
        } else {
            model.addAttribute("message", "success");
        }
        return "redirect:/home";
    }

    @PostMapping("/create/course")
    public String create(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("files") MultipartFile avatar,
                           Model model, Authentication authentication) throws IOException {
        User user = (User)authentication.getPrincipal();
        String res = creatorService.addCourse(user.getId(), name, description, avatar);
        if (res.equals("exists")){
            model.addAttribute("message", "exists");
        } else {
            model.addAttribute("message", "success");
        }
        return "redirect:/home";
    }
}
