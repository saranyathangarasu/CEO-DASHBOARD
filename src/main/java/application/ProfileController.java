package application;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.model.User;
import application.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute("user") @Valid User updatedUser, 
                              BindingResult bindingResult, 
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        try {
            userService.updateUser(updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
        }
        return "redirect:/profile"; 
    }

}
