package com.example.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class EmailHistoryDTO{
        private Integer id;
        private String message;
        @Email(message = "email not valid")
        private String email;
        private LocalDateTime createdDate=LocalDateTime.now();
}
