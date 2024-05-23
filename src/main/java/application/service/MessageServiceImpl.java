package application.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired; // Add this import
import org.springframework.stereotype.Service;

import application.model.Message;
import application.repo.MessageRepository; // Add this import

@Service
public class MessageServiceImpl implements MessageService {

    private final Map<String, MessageCallback> connectedClients = new ConcurrentHashMap<>();
    private final List<Message> messages = new ArrayList<>();


    @Autowired
    private MessageRepository messageRepository;


    @Override
    public void addClient(String username, MessageCallback callback) {
        connectedClients.put(username, callback);
    }

    @Override
    public void removeClient(String username) {
        connectedClients.remove(username);
    }
    @Override
    public void saveMessage(Message message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();
        message.setSender(loggedInUsername);
        messageRepository.save(message);
        messages.add(message);
        broadcastMessage(message);
    }

    @Override
    public void saveMessageWithSender(Message message, String senderUsername) {
        message.setSender(senderUsername);
        messageRepository.save(message);
        messages.add(message);
        broadcastMessage(message);
    }

    @Override
    public void broadcastMessage(Message message) {
        connectedClients.forEach((username, callback) -> callback.sendMessage(message));
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public List<Message> getConversation(String loggedInUsername, String selectedUsername) {
        return messageRepository.findBySenderAndReceiverOrSenderAndReceiverOrderByTimestamp(
                loggedInUsername, selectedUsername, selectedUsername, loggedInUsername);
    }
}
