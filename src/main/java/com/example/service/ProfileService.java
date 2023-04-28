package com.example.service;

import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.exps.AppBadRequestException;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto, Integer adminId) {
        // check - homework
        isValidProfile(dto);

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword())); // MD5 ?
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setStatus(GeneralStatus.ACTIVE);
        entity.setPrtId(adminId);
        profileRepository.save(entity); // save profile

        dto.setPassword(null);
        dto.setId(entity.getId());
        return dto;
    }

    public void isValidProfile(ProfileDTO dto) {
        // throw ...
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new AppBadRequestException("Profile not found");
        });
    }

    public ProfileDTO update(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        profileRepository.save(entity);
        dto.setPassword("****");
        dto.setRole(null);
        dto.setEmail("*****@gmail.com");
        return dto;
    }

    public List<ProfileDTO> getAll() {
        Iterable<ProfileEntity> profileEntities = profileRepository.findAll();
        List<ProfileDTO> profileDTOList = new LinkedList<>();
        profileEntities.forEach(profileEntity -> {
            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setId(profileEntity.getId());
            profileDTO.setName(profileEntity.getName());
            profileDTO.setSurname(profileEntity.getSurname());
            profileDTO.setPhone(profileEntity.getPhone());
            profileDTO.setEmail(profileEntity.getEmail());
            profileDTO.setRole(profileEntity.getRole());
            profileDTO.setPassword(profileEntity.getPassword());
            profileDTOList.add(profileDTO);
        });
        return profileDTOList;
    }

    public ProfileDTO updateDetail(ProfileDTO dto, Integer id) {
        if (getById(dto.getId()) == null && dto.getId() != id) {
            throw new AppBadRequestException("Wrong user");
        }
        profileRepository.save(updateDTO(dto));
        dto.setPassword("*****");
        return dto;
    }

    public ProfileEntity updateDTO(ProfileDTO dto) {
        ProfileEntity entity = getById(dto.getId());
        if (!dto.getEmail().isBlank() || dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (!dto.getPhone().isBlank() || dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
        if (!dto.getName().isBlank() || dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (!dto.getSurname().isBlank() || dto.getSurname() != null) {
            entity.setSurname(dto.getSurname());
        }
        if (!dto.getPassword().isBlank() || dto.getPassword() != null) {
            entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        }
        return entity;
    }

    private ProfileEntity getById(Integer id) {
        return profileRepository.findById(id).orElse(null);
    }

    public ProfileDTO delete(Integer id) {
        ProfileEntity profileEntity = getById(id);
        profileRepository.delete(profileEntity);
        ProfileDTO profileDTO=new ProfileDTO();
        profileDTO.setId(profileEntity.getId());
        profileDTO.setName(profileEntity.getName());
        profileDTO.setSurname(profileEntity.getSurname());
        profileDTO.setPhone(profileEntity.getPhone());
        profileDTO.setEmail(profileEntity.getEmail());
        profileDTO.setRole(profileEntity.getRole());
        profileDTO.setPassword(profileEntity.getPassword());
        return profileDTO;
    }
}
