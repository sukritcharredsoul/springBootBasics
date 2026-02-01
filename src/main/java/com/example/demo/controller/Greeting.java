package com.example.demo.controller;

import com.example.demo.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greeting {

//    @GetMapping("/greeting/{name}")
//    public Message greeting(@PathVariable String name){
//        return new Message("Meowww " + name,1) ;
//    }
//
//    @GetMapping("/salutations/")
//    public Message salutations(@RequestParam String name){
//        return new Message("saula" + name,2) ;
//    }
}
