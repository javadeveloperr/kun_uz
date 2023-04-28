package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.RegionEntity;
import com.example.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    public ArticleDTO create(ArticleDTO dto, Integer moderId) {
        // check
        ProfileEntity moderator = profileService.get(moderId);
        RegionEntity region = regionService.get(dto.getRegionId());
        CategoryEntity category = categoryService.get(dto.getCategoryId());

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderId);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        // type
        articleRepository.save(entity);
        return dto;
    }

    public ArticleDTO updateById(ArticleDTO dto) {
        return dto;
    }
}
