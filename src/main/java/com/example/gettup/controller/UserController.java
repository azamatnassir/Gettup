package com.example.gettup.controller;

import com.example.gettup.entity.Course;
import com.example.gettup.entity.User;
import com.example.gettup.service.CourseService;
import com.example.gettup.service.CreatorService;
import com.example.gettup.service.StudentService;
import com.example.gettup.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final CreatorService creatorService;
    private final StudentService studentService;
    private final CourseService courseService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("home", "active");
        return "general/index";
    }

    @GetMapping("/pricing")
    public String pricing(Model model) {
        model.addAttribute("pricing", "active");
        return "general/pricing";
    }

    @GetMapping("/features")
    public String features(Model model) {
        model.addAttribute("features", "active");
        return "general/features";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("about", "active");
        return "general/about";
    }

    @GetMapping("/support")
    public String support(Model model) {
        model.addAttribute("support", "active");
        return "general/support";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
 
    @GetMapping("/home")
    public String home(Model model, Authentication authentication, HttpServletRequest request) {
        User user = (User)authentication.getPrincipal();
        if (request.isUserInRole("ROLE_STUDENT")){
            model.addAttribute("courses", studentService.getByUserID(user.getId()).getCourses());
        } else if (request.isUserInRole("ROLE_CREATOR")){
            model.addAttribute("courses", creatorService.getByUserID(user.getId()).getCourses());
        }
        model.addAttribute("home", "active");
        return "home";
    }

    @PostMapping("/course")
    public String course(@RequestParam("courseId") Long id, Model model) {
        Course course = courseService.getByID(id);
        model.addAttribute("course", course);
        model.addAttribute("home", "active");
        return "course";
    }

    @PostMapping("/register")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirm") String confirm,
                           @RequestParam("roles") List<Integer> roles,
                           Model model) {
        String res = userService.register(firstName, lastName, email, password, confirm, roles);
        if (res.equals("success")) {
            model.addAttribute("error", "Successfully registered");
            return "login";
        } else if (res.equals("notEqual")) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        } else {
            model.addAttribute("error", "User already exists");
            return "register";
        }
    }
}
