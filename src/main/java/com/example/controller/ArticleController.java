package com.example.controller;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleGetByTypeRequestDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.dto.jwt.JwtDTO;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/update/{id}")
    public ResponseEntity<ArticleDTO> update(@RequestBody @Valid ArticleDTO dto,
                                             @RequestHeader("Authorization") String authorization,
                                             @PathVariable("id") String articleId) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.updateById(dto, articleId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id, jwtDTO.getId()));
    }

    //    5. Get Last 5 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 5ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
    @GetMapping("/type/{id}/five")
    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByTypeId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
    }

    //    6. Get Last 3 Article By Types  ordered_by_created_date
//            (Berilgan types bo'yicha oxirgi 3ta pubished bo'lgan article ni return qiladi.)
//    ArticleShortInfo
    @PostMapping("/eight")
    public ResponseEntity<List<ArticleShortInfoDTO>> get8ByTypeIdExceptGivenIdList(@RequestBody ArticleGetByTypeRequestDTO dto) {
        return ResponseEntity.ok(articleService.getLast8ByTypeIdNotIncludeGivenIdList(dto.getIdList()));
    }
    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> getLast3ArticleByType(@PathVariable("id") Integer id,
                                                   @RequestParam String status,
                                                   @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.last3Article(ArticleStatus.valueOf(status), id));
    }
//    @GetMapping("/type/{id}/five")
//    public ResponseEntity<List<ArticleShortInfoDTO>> get5ByTypeId(@PathVariable("id") Integer id) {
//        return ResponseEntity.ok(articleService.getLast5ByTypeId(id));
//    }
}
