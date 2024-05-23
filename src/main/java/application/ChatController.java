package application;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import application.model.Message;
import application.model.User;
import application.repo.MessageRepository;
import application.repo.UserRepository;
import application.service.MessageService;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/chatRoom")
    public String chat(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom";
    }


    @PostMapping("/chat")
    @ResponseBody
    public void postMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        messageService.broadcastMessage(message);
    }
    
    
    @GetMapping("/chatRoomPage")
    public String chatRoom(Model model) {
        // Fetch users from the database
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom";
    }

    @GetMapping("/loadConversation/{loggedInUsername}/{clickedUsername}")
    @ResponseBody
    public List<Message> loadConversation(
            @PathVariable String loggedInUsername,
            @PathVariable String clickedUsername) {
        List<Message> messages = messageService.getConversation(loggedInUsername, clickedUsername);
        return messages;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        try {
            messageService.saveMessage(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving message");
        }
    }

    @GetMapping("/chatRoom1")
    public String chat1(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom1";
    }
    
    @GetMapping("/chatRoomPage1")
    public String chatRoom1(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom1";
    }
    
    @GetMapping("/chatRoom2")
    public String chat2(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom2";
    }
    
    @GetMapping("/chatRoomPage2")
    public String chatRoom2(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom2";
    }
    
    @GetMapping("/chatRoom3")
    public String chat3(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom3";
    }
    
    @GetMapping("/chatRoomPage3")
    public String chatRoom3(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom3";
    }
    @GetMapping("/chatRoom4")
    public String chat4(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom4";
    }
    
    @GetMapping("/chatRoomPage4")
    public String chatRoom4(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom4";
    }
    @GetMapping("/chatRoom5")
    public String chat5(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom5";
    }
    
    @GetMapping("/chatRoomPage5")
    public String chatRoom5(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom5";
    }
    @GetMapping("/chatRoom6")
    public String chat6(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom6";
    }
    
    @GetMapping("/chatRoomPage6")
    public String chatRoom6(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom6";
    }
    @GetMapping("/chatRoom7")
    public String chat7(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom7";
    }
    
    @GetMapping("/chatRoomPage7")
    public String chatRoom7(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom7";
    }
    @GetMapping("/chatRoom8")
    public String chat8(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom8";
    }
    
    @GetMapping("/chatRoomPage8")
    public String chatRoom8(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom8";
    }
    @GetMapping("/chatRoom9")
    public String chat9(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom9";
    }
    
    @GetMapping("/chatRoomPage9")
    public String chatRoom9(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom9";
    }
    @GetMapping("/chatRoom10")
    public String chat10(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        model.addAttribute("loggedInUsername", loggedInUsername);
        return "chatRoom10";
    }
    
    @GetMapping("/chatRoomPage10")
    public String chatRoom10(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "chatRoom10";
    }
}

