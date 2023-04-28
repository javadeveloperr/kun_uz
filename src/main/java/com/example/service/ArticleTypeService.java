package com.example.service;

import com.example.dto.article.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.exps.AppBadRequestException;
import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        ArticleTypeEntity articleTypeEntity=new ArticleTypeEntity();
        articleTypeEntity.setName_uz(dto.getNameUz());
        articleTypeEntity.setName_ru(dto.getNameRu());
        articleTypeEntity.setName_en(dto.getNameEn());
        articleTypeRepository.save(articleTypeEntity);
        dto.setId(articleTypeEntity.getId());
        return dto;
    }
    public ArticleTypeDTO updateById(ArticleTypeDTO dto) {
        if (getById(dto.getId())==null){
            throw new AppBadRequestException("Article type not found");
        }
        articleTypeRepository.save(updateDto(dto));
        dto.setId(updateDto(dto).getId());
        return dto;
    }
    public ArticleTypeDTO deleteById(Integer id) {
        if (getById(id)==null){
            throw new AppBadRequestException("Article type not found");
        }
        ArticleTypeDTO dto=new ArticleTypeDTO();
        dto.setId(getById(id).getId());
        dto.setNameUz(getById(id).getName_uz());
        dto.setNameRu(getById(id).getName_ru());
        dto.setNameEn(getById(id).getName_en());
        articleTypeRepository.deleteById(id);
        return dto;
    }
    public List<ArticleTypeEntity> getList(){
        List<ArticleTypeEntity> articleTypeEntities= (List<ArticleTypeEntity>) articleTypeRepository.findAll();
        return articleTypeEntities;
    }
    public ArticleTypeEntity getById(Integer id) {
        return articleTypeRepository.findById(id).orElse(null);
    }
    public ArticleTypeEntity updateDto(ArticleTypeDTO dto) {
        ArticleTypeEntity entity = getById(dto.getId());
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
