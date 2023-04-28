package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleTypeDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping({"", "/"})
    public ResponseEntity<ArticleDTO> create(@RequestBody @Valid ArticleDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }
    @PostMapping( "/update")
    public ResponseEntity<ArticleDTO> update(@RequestBody @Valid ArticleDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.updateById(dto));
    }
}
