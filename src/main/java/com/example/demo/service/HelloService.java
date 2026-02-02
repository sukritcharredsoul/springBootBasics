package com.example.demo.service;

import com.example.demo.dto.MessageRequest;
import com.example.demo.dto.MessageResponse;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {

    private final MessageRepository repository;

    public HelloService(MessageRepository repository){
       this.repository = repository ;
    }

    public List<MessageResponse> getMessages(){
        return repository.findAll().stream().map(m -> new MessageResponse(m.getId(),m.getText())).toList() ;
    }

    public MessageResponse addMessage(MessageRequest request){

        System.out.println("Received message: " + request.getMessage());
        Message saved = repository.save(new Message(request.getMessage())) ;
        return new MessageResponse(saved.getId(),saved.getText()) ;
    }

    public MessageResponse getMessage(Integer id) {
        return (MessageResponse) repository.findById(id).stream().map(m -> new MessageResponse(m.getId(),m.getText()));
    }

    public MessageResponse getById(Integer id){
        Message message = repository.findById(id).orElseThrow(() -> new ResourceNotFound("Resource Not found")) ;
        return new MessageResponse(message.getId(),message.getText()) ;
    }

    public void deleteMessage(Integer id){
        repository.deleteById(id);
    }


}
