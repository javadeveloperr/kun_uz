package com.example.service;

import com.example.entity.EmailHistoryEntity;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public List<EmailHistoryEntity> getEmailHistoryByEmail(String email) {
        return emailHistoryRepository.findEmailHistoryEntitiesByEmail(email);
    }

    public List<EmailHistoryEntity> getEmailHistoryByDate(LocalDate fromDate) {
        return emailHistoryRepository.findEmailHistoryEntitiesByCreatedDate_Date(fromDate);
    }
}
