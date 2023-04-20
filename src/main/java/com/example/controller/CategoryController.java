package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.JwtDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping({"", "/"})
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.create(dto));
    }
    @PostMapping( "/update")
    public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.updateById(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> delete(@PathVariable Integer id,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.deleteById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<CategoryEntity>> getList(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(categoryService.getList());
    }
}
