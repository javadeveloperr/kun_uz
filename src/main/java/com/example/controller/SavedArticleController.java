package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.SavedArticleDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.SavedArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saved-article")
@AllArgsConstructor
public class SavedArticleController {
    private SavedArticleService savedArticleService;
    @PostMapping({"", "/"})
    public ResponseEntity<?> create(@RequestBody @Valid SavedArticleDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(savedArticleService.create(dto));
    }
}
