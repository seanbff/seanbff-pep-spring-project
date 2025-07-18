package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//@RequestMapping("/api")

public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {        
            Account createdAccount = accountService.addAccount(account);
            return ResponseEntity.ok(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(loggedInAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        Message createdMessage = messageService.addMessage(message);
        if (createdMessage == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getOneMessage(@PathVariable int message_id) {
        Message message = messageService.getOneMessage(message_id);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable int message_id) {
        int result = messageService.deleteMessage(message_id);
        if (result == 1) {
            return ResponseEntity.ok(1);
        } else return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessage(@PathVariable int message_id, @RequestBody Message messageBody) {
        int result = messageService.updateMessage(message_id, messageBody.getMessageText());
        if (result == 1) {
            return ResponseEntity.ok(1);
        } else return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int account_id) {
        List<Message> messages = messageService.getMessagesByAccountId(account_id);
        return ResponseEntity.ok(messages);
    }
}
