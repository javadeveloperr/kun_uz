package com.example.service;

import com.example.dto.article.ArticleDTO;
import com.example.dto.article.ArticleShortInfoDTO;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.exps.ItemNotFoundException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final AttachService attachService;
    public ArticleDTO create(ArticleDTO dto, Integer moderId) {
        // check
//        ProfileEntity moderator = profileService.get(moderId);
//        RegionEntity region = regionService.get(dto.getRegionId());
//        CategoryEntity category = categoryService.get(dto.getCategoryId());

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

    public ArticleDTO updateById(ArticleDTO dto, String id) {
        ArticleEntity entity = get(id);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);
        return dto;
    }
    public ArticleEntity get(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + id);
        }
        return optional.get();
    }

    public boolean delete(Integer id) {
        ArticleEntity entity = articleRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Article not found");
        }
        entity.setVisible(false);
        articleRepository.save(entity);
        return true;
    }

    public Boolean changeStatus(ArticleStatus status, String id, Integer prtId) {
        ArticleEntity entity = get(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(prtId);
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        // articleRepository.changeStatus(status, id);
        return true;
    }
    public ArticleShortInfoDTO toArticleShortInfo(ArticleEntity entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setImage(attachService.getAttachLink(String.valueOf(entity.getAttachId())));
        return dto;
    }
//    public List<ArticleShortInfoDTO> getLast5ByTypeId(Integer typeId) {
//        List<ArticleEntity> entityList = articleRepository.findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(typeId,
//                ArticleStatus.PUBLISHED, true);
//        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
//        entityList.forEach(entity -> {
//            dtoList.add(toArticleShortInfo(entity));
//        });
//        return dtoList;
//    }
public List<ArticleShortInfoDTO> getLast5ByTypeId(Integer typeId) {
    List<ArticleShortInfoMapper> entityList = articleRepository.find5ByTypeIdNative(typeId, ArticleStatus.PUBLISHED.name(), 5);
    List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
    entityList.forEach(entity -> {
        dtoList.add(toArticleShortInfo(entity));
    });
    return dtoList;
}
    public ArticleShortInfoDTO toArticleShortInfo(ArticleShortInfoMapper entity) {
        ArticleShortInfoDTO dto = new ArticleShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublished_date());
        dto.setImage(attachService.getAttachLink(entity.getAttachId()));
        return dto;
    }

    public Object last5Article(ArticleStatus valueOf, String id) {
        return null;
    }

    public Object last3Article(ArticleStatus valueOf, Integer id) {
        return null;
    }

    public List<ArticleShortInfoDTO> getLast8ByTypeIdNotIncludeGivenIdList(List<String> idList) {
        List<ArticleEntity> entityList = articleRepository.find8ByTypeIdExceptIdLists(ArticleStatus.PUBLISHED, idList);
        List<ArticleShortInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }
}
