package com.azilen.training.controller;

import com.azilen.training.dto.RegisterDTO;
import com.azilen.training.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showSignupPage(Model model) {
        model.addAttribute("registerDTO",new RegisterDTO());
        return "register";
    }

    @PostMapping("/save-register")
    public String registerUser(@Valid  @ModelAttribute RegisterDTO registerDTO, BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getFieldError());
            return "register";
        }

        try {
            Boolean isSignup = userService.signUpUser(registerDTO);
            if (isSignup) {
                model.addAttribute("successMsg", "Register successfully!");
                return "login";
            }
            model.addAttribute("error", "Registration failed! Please try again.");
            return "register";
        }
        catch (IllegalArgumentException e){
            model.addAttribute("error",e.getMessage());
            return "register";
        }
        catch (Exception e){
            model.addAttribute("error","Something went wrong!!");
            return "register";
        }
    }
  }
