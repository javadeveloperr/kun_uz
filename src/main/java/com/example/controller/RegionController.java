package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.dto.region.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PostMapping({"", "/"})
    public ResponseEntity<RegionDTO> create(@RequestBody @Valid RegionDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.create(dto));
    }
    @PostMapping( "/update")
    public ResponseEntity<RegionDTO> update(@RequestBody @Valid RegionDTO dto,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.updateById(dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<RegionDTO> delete(@PathVariable Integer id,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.deleteById(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<RegionEntity>> getList(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.getList());
    }
}
