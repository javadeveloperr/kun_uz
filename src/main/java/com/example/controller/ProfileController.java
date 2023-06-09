package com.example.controller;

import com.example.dto.jwt.JwtDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping({"", "/"})
    public ResponseEntity<ProfileDTO> create(@RequestBody @Valid ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(profileService.create(dto, jwtDTO.getId()));
    }
    @PostMapping("/update")
    public ResponseEntity<ProfileDTO> update(@RequestBody @Valid ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(profileService.update(dto));
    }
    @PostMapping("/update-detail")
    public ResponseEntity<ProfileDTO> updateDetail(@RequestBody @Valid ProfileDTO dto,
                                             @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        return ResponseEntity.ok(profileService.updateDetail(dto, jwtDTO.getId()));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProfileDTO>> getAll(@RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(profileService.getAll());    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable("id") Integer id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDTO> deleteById(@PathVariable("id") Integer id,
                                                 @RequestHeader("Authorization") String authorization) {
        JwtDTO jwtDTO = JwtUtil.authorization(authorization);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<ProfileDTO>> pagination(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        return null;
    }


}
