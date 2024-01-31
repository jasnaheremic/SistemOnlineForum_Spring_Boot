package org.example.forum.controller;

import jakarta.servlet.http.HttpSession;
import org.example.forum.service.Discussions.DiscussionService;
import org.example.forum.service.Discussions.persistence.DiscussionEntity;
import org.example.forum.service.Users.UserService;
import org.example.forum.service.Users.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final DiscussionService discussionService;

    @Autowired
    public UserController(UserService userService, DiscussionService discussionService) {
        this.userService = userService;
        this.discussionService = discussionService;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/registration")
    public String register(String username, String password, Model model) {
        System.out.println("Entering registration POST Request.");
        UserEntity user = userService.register(username, password);

        if (user != null) {
            model.addAttribute("success", "Successfully registered. You can login now!");
            return "login";
        } else {
            model.addAttribute("errorMessage", "Username exists, try something else.");
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        UserEntity user = userService.login(username, password);

        if (user != null) {
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("message", "Invalid credentials. Please try again.");
            return "login";
        }
    }
}
