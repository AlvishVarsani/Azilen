package com.azilen.training.controller;

import com.azilen.training.dto.ChangePasswordDTO;
import com.azilen.training.dto.RegisterDTO;
import com.azilen.training.dto.UserUpdateDTO;
import com.azilen.training.entity.User;
import com.azilen.training.entity.UserDetails;
import com.azilen.training.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(Model model, HttpSession session){
      User user= (User) session.getAttribute("user");
      model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }

        model.addAttribute("user",user);
        return "edit-profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute UserUpdateDTO updateDto,
                                HttpSession session,
                                Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        try {

            boolean updated = userService.updateUserProfile(sessionUser, updateDto);
           // this use for updating the session after changing details
            User refreshedUser = userService.findByUserName(sessionUser.getUsername());
            session.setAttribute("user", refreshedUser);
            model.addAttribute("user",refreshedUser);
            return "profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "edit-profile";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred");
            return "edit-profile";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordDTO changePasswordDTO,
                                 BindingResult bindingResult,
                                 HttpSession session,
                                 Model model) throws Exception {
        try{
            if (bindingResult.hasErrors()) {
                model.addAttribute("errors",bindingResult.getFieldError());
                return "change-password";
            }

            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            if (!userService.matchesOldPassword(user, changePasswordDTO.getOldPassword())) {
                model.addAttribute("error","Old passwords does not match");
                return "change-password";
            }
            if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
                model.addAttribute("error", "New password must be different from the old password");
                return "change-password";
            }
            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                model.addAttribute("error","Password and confirm password should match");
                return "change-password";
            }
            boolean isUpdated = userService.changePassword(user, changePasswordDTO.getNewPassword());
            if (!isUpdated) {
                model.addAttribute("error","Something went wrong while changing passwords");
                return "change-password";
            }
            model.addAttribute("message", "Password changed successfully.");
            return "change-password";
        }
        catch (Exception e){
            model.addAttribute("error",e.getMessage());
           return "change-password";
        }
        }

}
