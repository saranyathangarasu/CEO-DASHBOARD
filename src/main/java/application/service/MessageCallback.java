package application.service;

import application.model.Message;

public interface MessageCallback {
    void sendMessage(Message message);
}
