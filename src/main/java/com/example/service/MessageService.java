package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.repository.MessageRepository;
import com.example.entity.Message;
import com.example.entity.Account;
import java.util.List;

@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;
    AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }
    
    public Message addMessage(Message message) {
        String message_text = message.getMessageText();
        int posted_by = message.getPostedBy();
        Account existingAccount = accountService.getAccountById(posted_by);
        if (existingAccount == null) return null;
        if (message_text.isEmpty() || message_text.length() > 254) return null;
        return messageRepository.save(message);
    }
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getOneMessage(int message_id) {
        return messageRepository.findById(message_id).orElse(null);
    }

    public int deleteMessage(int message_id) {
        Message message = messageRepository.findById(message_id).orElse(null);
        if (message != null) {
            messageRepository.delete(message);
            return 1;
        } else return 0;
    }
        
    public int updateMessage(int message_id, String message_text) {
        Message message = messageRepository.findById(message_id).orElse(null);
        if (message_text.isEmpty() || message == null || message_text.length() > 255) return 0;
        message.setMessageText(message_text);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccountId (int account_id) {
        return messageRepository.findByPostedBy(account_id);
    }

    
}
