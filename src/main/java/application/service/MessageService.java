package application.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import application.model.Message;

public interface MessageService {

    void addClient(String username, MessageCallback callback);

    void removeClient(String username);

    void saveMessage(Message message);

    void broadcastMessage(Message message);

    List<Message> getAllMessages();

    void saveMessageWithSender(Message message, String senderUsername);
    List<Message> getConversation(String loggedInUsername, String selectedUsername);
}
