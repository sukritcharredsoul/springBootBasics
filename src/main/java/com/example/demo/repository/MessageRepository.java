package com.example.demo.repository;

import com.example.demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.* ;

public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findByTextContainingIgnoreCase(String keyword) ;


}
