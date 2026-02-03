package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.model.Message;
import com.example.demo.dto.MessageRequest;
import com.example.demo.service.HelloService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HelloController {

    private final HelloService helloService ;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }



    @GetMapping("/messages")
    public Page<MessageResponse> messages(Pageable pageable){
        return helloService.getMessages(pageable) ;
    }



    @PostMapping("/messages/")
    public ResponseEntity<MessageResponse> createMessage(@Valid @RequestBody MessageRequest request){
        MessageResponse response = helloService.addMessage(request) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(response) ;
    }


    @GetMapping("/messages/{id}")
    public MessageResponse getMessage(@PathVariable Integer id){
        return helloService.getById(id) ;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Integer id){
        helloService.deleteMessage(id);
        return ResponseEntity.noContent().build() ;
    }


    @GetMapping("/search")
    public MessageResponse searchMessage(@PathVariable String keyword) {
        return (MessageResponse) helloService.search(keyword).stream().map(m -> new MessageResponse(m.getId(), m.getText()));
    }

}
