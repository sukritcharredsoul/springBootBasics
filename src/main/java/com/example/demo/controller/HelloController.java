package com.example.demo.controller;

import com.example.demo.dto.MessageResponse;
import com.example.demo.model.Message;
import com.example.demo.dto.MessageRequest;
import com.example.demo.service.HelloService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class HelloController {

    private final HelloService helloService ;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }



    @GetMapping("/messages")
    public List<MessageResponse> messages(){
        return helloService.getMessages() ;
    }

    @PostMapping("/messages")
    public MessageResponse createMessage(@RequestBody MessageRequest request){
        return helloService.addMessage(request) ;
    }

    @GetMapping("/messages/{id}")
    public MessageResponse message(@PathVariable int id){return helloService.getMessage(id);}  ;


}
