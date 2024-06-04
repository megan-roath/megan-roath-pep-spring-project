package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    /* 
     * Retrieve all messages.
     * @return all the messages in the message table
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /*
     * Retrieve a message by it's messageId
     * 
     * @param messageId unique identifier for the message to be retrieved.
     * @return the specific message
     */
    public Message getMessageBymessageId(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }

    /*
     * Retrieve all messages written by a specific user.
     * 
     * @param postedBy unique identifier to a user
     * @return a list of all messages the user has posted
     */
    public List<Message> getMessagesByAccountId(int accountId){
        List<Message> messages = messageRepository.findAll();
        List<Message> accountMessages = new ArrayList<Message>();

        for(Message message : messages){
            if(message.getPostedBy() == accountId){
                accountMessages.add(message);
            }
        }
        return accountMessages;
    }

    /*
     * Process the creation of new messages
     * 
     * @param message Message to be input into the database
     * @return the new message
     */
    public Message createMessage(Message message){
        String messageText = message.getMessageText();

        if(messageText.equals("") || messageText.length() > 254){
            return null;
        }

        if(messageRepository.findBypostedBy(message.getPostedBy()).isEmpty()){
            return null;
        }
        return messageRepository.save(message);
    }

    /*
     * Update a message text identified by a message ID
     * 
     * @param messageId ID of the message to be updated
     * @param messageText the new text for the message to be updated to
     * @return the newly updated message
     */
    public int updateMessage(Message message){
        int messageId = message.getMessageId();
        String messageText = message.getMessageText();
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if(optionalMessage.isEmpty() || messageText.length()>255 || messageText.equals("")){
            return 0;
        }

        Message updated = optionalMessage.get();
        updated.setMessageText(messageText);
        messageRepository.save(updated);
        
        return 1;
    }

        

    /*
     * Delete a message identified by a messageId.
     * MessageId must exist
     * 
     * @param messageId unique ID for the message to be deleted.
     */
    public int deleteMessage(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }
}
