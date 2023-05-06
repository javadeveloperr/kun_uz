package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer>, PagingAndSortingRepository<SavedArticleEntity, Integer> {
}
