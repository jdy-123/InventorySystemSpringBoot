package com.inventorySystem.controller;

import com.inventorySystem.dto.UserDto;
import com.inventorySystem.Model.User;
import com.inventorySystem.service.impl.User.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/homePage")
    public String home(Model model) {
        // Get authenticated user's email
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // Assuming the email is stored as username in authentication
        String password = ""; // Assuming the email is stored as username in authentication

        // Fetch user from the database
        User user = userService.findByUsername(username);

        // Extract first name
        String name = user.getName(); // Assuming 'getFirstName()' exists in your User entity

        // Pass first name to the template
        model.addAttribute("name", name);

        return "homePage"; // Ensure this matches your Thymeleaf template name
    }


    @GetMapping("/login")
    public String loginForm() {
        return "index";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByUsername(user.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);  // Call the service method to delete the user
        return "redirect:/users";  // Redirect back to the list of users
    }


}
