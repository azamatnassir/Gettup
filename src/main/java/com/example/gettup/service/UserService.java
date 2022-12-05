package com.example.gettup.service;

import com.example.gettup.entity.Creator;
import com.example.gettup.entity.Role;
import com.example.gettup.entity.Student;
import com.example.gettup.entity.User;
import com.example.gettup.repository.RoleRepository;
import com.example.gettup.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final String USER_NOT_FOUND = "User with email: %s not found";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CreatorService creatorService;
    private final StudentService studentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String register(String firstName, String lastName, String email, String password, String confirm, List<Integer> roles) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            return "error";
        }

        if (!password.equals(confirm)) {
            return "notEqual";
        }

        User user = new User(firstName + " " + lastName, email, bCryptPasswordEncoder.encode(password), false, true);
        Set<Role> roleSet = new HashSet<>();

        if (roles.contains(2)) {
            roleSet.add(roleRepository.findRoleById(2L));
            Creator creator = new Creator();
            creator.setUser(user);
            creatorService.save(creator);
        }

        if (roles.contains(3)) {
            roleSet.add(roleRepository.findRoleById(3L));
            Student student = new Student();
            student.setUser(user);
            studentService.save(student);
        }

        user.setRoles(roleSet);
        userRepository.save(user);
        return "success";
    }

}
