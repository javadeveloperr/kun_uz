package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {
    List<EmailHistoryEntity> findEmailHistoryEntitiesByEmail(String email);

    List<EmailHistoryEntity> findEmailHistoryEntitiesByCreatedDate_Date(LocalDate fromDate);
}
