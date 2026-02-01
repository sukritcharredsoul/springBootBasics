package com.example.demo.service;

import com.example.demo.dto.MessageResponse;
import com.example.demo.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {

    private final List<Message> messages = new ArrayList<>() ;

    public HelloService(){
        messages.add(new Message("First",1)) ;
        messages.add(new Message("second",2)) ;
    }

    public List<MessageResponse> getMessages(){
        return messages.stream().map(m -> new MessageResponse(m.getId(),m.getText())).toList() ;
    }

    public Message addMessage(String text){
        int id = messages.size() + 1 ;
        Message message = new Message(text,id)  ;
        messages.add(message) ;
        return message ;
    }

    public MessageResponse getMessage(int id) {
        for (Message message : messages) {
            if (id == message.getId()) {
                return new MessageResponse(message.getId(), message.getText());
            }
        }
        return new MessageResponse(-1,"Couldn't Find Id") ;

    }
}
