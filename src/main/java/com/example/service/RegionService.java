package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.exps.AppBadRequestException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;
    public RegionDTO create(RegionDTO dto) {
        RegionEntity regionEntity=new RegionEntity();
        regionEntity.setName_uz(dto.getNameUz());
        regionEntity.setName_ru(dto.getNameRu());
        regionEntity.setName_en(dto.getNameEn());
        regionRepository.save(regionEntity);
        dto.setId(regionEntity.getId());
        return dto;
    }
    public RegionDTO updateById(RegionDTO dto) {
        if (getById(dto.getId())==null){
            throw new AppBadRequestException("Region not found");
        }
        regionRepository.save(updateDto(dto));
        dto.setId(updateDto(dto).getId());
        return dto;
    }
    public RegionDTO deleteById(Integer id) {
        if (getById(id)==null){
            throw new AppBadRequestException("Article type not found");
        }
        RegionDTO dto=new RegionDTO();
        dto.setId(getById(id).getId());
        dto.setNameUz(getById(id).getName_uz());
        dto.setNameRu(getById(id).getName_ru());
        dto.setNameEn(getById(id).getName_en());
        regionRepository.deleteById(id);
        return dto;
    }
    public List<RegionEntity> getList(){
        List<RegionEntity> categoryEntities= (List<RegionEntity>) regionRepository.findAll();
        return categoryEntities;
    }
    public RegionEntity getById(Integer id) {
        return regionRepository.findById(id).orElse(null);
    }
    public RegionEntity updateDto(RegionDTO dto) {
        RegionEntity entity = getById(dto.getId());
        if (!dto.getNameUz().isBlank() || dto.getNameUz() != null) {
            entity.setName_uz(dto.getNameUz());
        }
        if (!dto.getNameRu().isBlank() || dto.getNameRu() != null) {
            entity.setName_ru(dto.getNameRu());
        }
        if (!dto.getNameEn().isBlank() || dto.getNameEn() != null) {
            entity.setName_en(dto.getNameEn());
        }
        return entity;
    }
}
