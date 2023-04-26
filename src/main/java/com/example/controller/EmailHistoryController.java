package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.RegistrationResponseDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/email/history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    //        2. Get EmailHistory by email
//            (id, email,message,created_date)
    @GetMapping("/{email}")
    public ResponseEntity<List<EmailHistoryEntity>> getEmailHistoryByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(emailHistoryService.getEmailHistoryByEmail(email));
    }

    //    3. Get EmailHistory  by given date -?
//            (id, email,message,created_date)
    @GetMapping("/{fromDate}")
    public ResponseEntity<List<EmailHistoryEntity>> getEmailHistoryByDate(@PathVariable("fromDate") LocalDate fromDate) {
        return ResponseEntity.ok(emailHistoryService.getEmailHistoryByDate(fromDate));
    }
}
