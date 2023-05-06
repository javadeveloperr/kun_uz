package com.example.service;

import com.example.dto.article.SavedArticleDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.SavedArticleEntity;
import com.example.repository.ArticleRepository;
import com.example.repository.ProfileRepository;
import com.example.repository.SavedArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SavedArticleService {
    private ArticleRepository articleRepository;
    private ProfileRepository profileRepository;
    private SavedArticleRepository savedArticleRepository;
    public ArticleEntity getArticle(Integer id){
        return articleRepository.findById(id).orElse(null);
    }
    public ProfileEntity getProfile(Integer id){
        return profileRepository.findById(id).orElse(null);
    }
    public String create(SavedArticleDTO dto) {
        ArticleEntity articleEntity = getArticle(Integer.getInteger(dto.getArticleId()));
        ProfileEntity profile = getProfile(dto.getProfileId());
        if (articleEntity==null || profile==null){
            return "Profile or Article not found";
        }
        SavedArticleEntity savedArticleEntity=new SavedArticleEntity();
        savedArticleEntity.setArticleEntity(articleEntity);
        savedArticleEntity.setProfileId(profile);
        savedArticleRepository.save(savedArticleEntity);
        return "Successfully saved !";
    }
    public SavedArticleEntity getSavedArticle(Integer id){
        return savedArticleRepository.findById(id).orElse(null);
    }
    public String delete(Integer id){
        SavedArticleEntity savedArticle = getSavedArticle(id);
        if (savedArticle==null){
            return "Article not found";
        }
        savedArticleRepository.deleteById(id);
        return "Successfully deleted";
    }
}
