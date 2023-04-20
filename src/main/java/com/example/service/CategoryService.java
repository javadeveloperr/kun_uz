package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exps.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity categoryEntity=new CategoryEntity();
        categoryEntity.setName_uz(dto.getNameUz());
        categoryEntity.setName_ru(dto.getNameRu());
        categoryEntity.setName_en(dto.getNameEn());
        categoryRepository.save(categoryEntity);
        dto.setId(categoryEntity.getId());
        return dto;
    }
    public CategoryDTO updateById(CategoryDTO dto) {
        if (getById(dto.getId())==null){
            throw new AppBadRequestException("Category not found");
        }
        categoryRepository.save(updateDto(dto));
        dto.setId(updateDto(dto).getId());
        return dto;
    }
    public CategoryDTO deleteById(Integer id) {
        if (getById(id)==null){
            throw new AppBadRequestException("Article type not found");
        }
        CategoryDTO dto=new CategoryDTO();
        dto.setId(getById(id).getId());
        dto.setNameUz(getById(id).getName_uz());
        dto.setNameRu(getById(id).getName_ru());
        dto.setNameEn(getById(id).getName_en());
        categoryRepository.deleteById(id);
        return dto;
    }
    public List<CategoryEntity> getList(){
        List<CategoryEntity> categoryEntities= (List<CategoryEntity>) categoryRepository.findAll();
        return categoryEntities;
    }
    public CategoryEntity getById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public CategoryEntity updateDto(CategoryDTO dto) {
        CategoryEntity entity = getById(dto.getId());
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
