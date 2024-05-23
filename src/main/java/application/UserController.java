package application;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import application.Dto.UserDto;
import application.model.User;
import application.service.EmailService;
import application.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    public UserController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    @GetMapping("/termsAndConditions")
    public String termsAndConditions() {
        return "termsAndConditions";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('HO_Company')")
    public String home(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("User Details: " + userDetails.getUsername());
        System.out.println("User Roles: " + userDetails.getAuthorities());
        int totalStudents = userService.getTotalStudents();
        int totalSalesWorkers = userService.getTotalWorkersByDepartment("Sales");
        int totalOperationalWorkers = userService.getTotalWorkersByDepartment("Operational");
        int totalFinanceWorkers = userService.getTotalWorkersByDepartment("Finance");
        model.addAttribute("userdetail", userDetails);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalSalesWorkers", totalSalesWorkers);
        model.addAttribute("totalOperationalWorkers", totalOperationalWorkers);
        model.addAttribute("totalFinanceWorkers", totalFinanceWorkers);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "register";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.user", "Passwords do not match.");
            return "register";
        }

        User existingUser = userService.findByUsername(userDto.getUsername());
        if (existingUser != null) {
            System.out.println("User already exists: " + existingUser.getUsername());
            model.addAttribute("usernameError", "Username already exists.");
            return "register";
        }

        if ("Others".equalsIgnoreCase(userDto.getDegree())) {
            userDto.setDegree(userDto.getOtherDegree());
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDegree(userDto.getDegree());

        try {
            userService.save(userDto);
            String emailSubject = "Welcome to Our Saranya Jash Corporation";
            String emailBody = "Thank you for registering " + userDto.getFirstname() + " !";
            emailService.sendEmail(userDto.getEmail(), emailSubject, emailBody);
            System.out.println("Welcome email sent successfully to: " + userDto.getEmail());
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Welcome email sent.");
            System.out.println("User registered successfully!");
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to send welcome email: " + e.getMessage());
            System.out.println("Email ID not found: " + userDto.getEmail());
            model.addAttribute("emailError", "Email ID not found. Please provide a valid email ID.");
            return "register";
        }
    }

    @GetMapping("/userOperationalDashboard")
    @PreAuthorize("hasAnyAuthority('HO_Company', 'Operationalhead')")
    public String userOperationalDashboard(Model model) {
        return "operationalDashboard";
    }

    @GetMapping("/userSalesDashboard")
    @PreAuthorize("hasAnyAuthority('HO_Company', 'Saleshead')")
    public String userSalesDashboard(Model model) {
        return "salesDashboard";
    }

    @GetMapping("/userFinanceDashboard")
    @PreAuthorize("hasAnyAuthority('HO_Company', 'Financehead')")
    public String userFinanceDashboard(Model model) {
        return "financeDashboard";
    }
    
    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("updatedUser", user);
        return "editUserForm";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("updatedUser") User updatedUser) {
        userService.updateUser(updatedUser);
        return "redirect:/users";
    }

}