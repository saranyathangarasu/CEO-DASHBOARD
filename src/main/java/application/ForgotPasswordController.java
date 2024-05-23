package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import application.model.User;
import application.repo.UserRepository;

import java.util.UUID;

@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("user", new User());
        return "forgotPassword";
    }
    @PostMapping("/checkCredentials") // Endpoint to check username and email combination
    @ResponseBody
    public boolean checkCredentials(@RequestParam("username") String username, @RequestParam("email") String email) {
        System.out.println("Received username: " + username);
        System.out.println("Received email: " + email);

        User user = userRepository.findByUsername(username);
        boolean valid = user != null && user.getEmail().equals(email);
        System.out.println("Credentials validation result: " + valid);

        return valid;
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(@RequestParam("username") String username,
                                            @RequestParam("email") String email,
                                            Model model,
                                            RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.getEmail().equals(email)) {
            model.addAttribute("formError", "Invalid username or email.");
            return "forgotPassword";
        }

        String newPassword = UUID.randomUUID().toString().substring(0, 8);

        // Update user's password in the database
        user.setPassword(passwordEncoder.encode(newPassword)); // Assuming User entity has setPassword method
        userRepository.save(user);

        sendPasswordResetEmail(user.getEmail(), newPassword);

        redirectAttributes.addFlashAttribute("successMessage", "Password reset instructions sent to your email.");
        return "redirect:/login";
    }


    private void sendPasswordResetEmail(String recipientEmail, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        message.setText("Your new password is: " + newPassword);
        emailSender.send(message);
    }
}
