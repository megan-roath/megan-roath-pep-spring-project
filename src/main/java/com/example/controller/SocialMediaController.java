package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;

    /*
     * Handler to process new User Registration.
     * 
     * @status 200 if registration is successful
     * @status 409 if the username already exists
     * @status 400 if not succesful
     */
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> addAccount(@RequestBody Account account){
        Account usernameEntry = accountService.getAccountByUsername(account.getUsername());
        if(usernameEntry!=null){
            return ResponseEntity.status(409).body(null);
        }
        
        Account registered = accountService.addAccount(account);
        if(registered != null){
            return ResponseEntity.status(200).body(registered);
        }
        return ResponseEntity.status(400).body(null);
    }

    /*
     * Handler to process User logins.
     * 
     * @status 200 if login was succesful
     * @status 401 if login was unsuccesful
     */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account login = accountService.saveAccount(account);
        if(login != null){
            return ResponseEntity.status(200).body(login);
        }
        return ResponseEntity.status(401).body(null);
    }
    

    /*
     * Handler to process the creation of new messages.
     * 
     * @status 200 if message creation is succesful
     * @status 400 if creation was not succesful
     */
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message msg = messageService.createMessage(message);
        if(msg != null){
            return ResponseEntity.status(200).body(msg);
        }
        return ResponseEntity.status(400).body(null);
    
    }

    /*
     * Handler to retrieve all messages.
     * 
     * @status 200
     */
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    /*
     * Handler to retrieve messages by specific user ID.
     * 
     * @status 200
     */
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageBymessageId(@PathVariable int messageId){
        Message message = messageService.getMessageBymessageId(messageId);
        return ResponseEntity.status(200).body(message);
    }

    /*
     * Handler to delete a message by a specific message ID.
     * 
     * @status 200
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        int updatedRows = messageService.deleteMessage(messageId);
        if(updatedRows == 1){
            return ResponseEntity.status(200).body(updatedRows);
        }
        return ResponseEntity.status(200).body(null);
    }

    /*
     * Handler to update a message text by it's message ID.
     * 
     * @status 200 if update was succesful
     * @status 400 if unsuccesful
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable int messageId){
       message.setMessageId(messageId);
       int updatedRows = messageService.updateMessage(message);
       if(updatedRows == 1){
            return ResponseEntity.status(200).body(updatedRows);
       }
       return ResponseEntity.status(400).body(updatedRows);
    }

    /*
     * Handler to retrieve all messages by a specific user.
     * 
     * @status 200
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByAccountId(@PathVariable int accountId){
        List<Message> result = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(result);
    }
}
