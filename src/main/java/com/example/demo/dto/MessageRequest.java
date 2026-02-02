package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageRequest{

    @NotBlank(message =  "Text cannot be empty")
    @Size(max = 255 , message = "You cannot exceed 255 characters.")
    private String message ;

    public String getMessage() {
        return message;
    }


}
