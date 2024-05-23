package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.model.User;
import application.service.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendWelcomeEmail")
    public String registerUser(@RequestBody User user) {
        // Save the user to the database or perform any necessary operations

        try {
            // Send a welcome email to the registered user
            emailService.sendEmail(user.getEmail(), "Welcome to Our Application", "Thank you for registering!");
            return "Registration successful. Welcome email sent.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send welcome email: " + e.getMessage();
        }
    }
}
