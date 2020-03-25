package com.voicetm.karaoke.controller;

import com.voicetm.karaoke.domain.Role;
import com.voicetm.karaoke.domain.User;
import com.voicetm.karaoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

@RestController
public class MainController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public MainController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/")
    public String  main(){
        User user = new User();
        user.setEmail("yorogac927@xmailsme.com");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setAvatar("default.png");
        user.setPhone("+99361616161");
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

        System.out.println("asdasd");
        //System.out.println(passwordEncoder.encode("MuzTM@2020"));
        //userService.save(user);
        return "Welcome to MuzTM visit <a href=\"https://develop.voicetm.com\">Our Web Site</a>";
    }

    @GetMapping("/about")
    public String about(){
        return "About Page";
    }

}
