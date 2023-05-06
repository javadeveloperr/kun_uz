package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfoMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    Optional<ArticleEntity> findById(Integer id);

    List<ArticleEntity> findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query("From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
    List<ArticleEntity> find5ByTypeId(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);
    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) " +
            "From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
    List<ArticleEntity> find5ByTypeId2(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:typeId and " +
            "status =:status order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> find5ByTypeIdNative(@Param("t_id") Integer typeId,
                                                     @Param("status") String status,
                                                     @Param("limit") Integer limit);

    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) From ArticleEntity " +
            "where status =:status and visible = true and id not in :idList " +
            " order by createdDate desc limit 8")
    List<ArticleEntity> find8ByTypeIdExceptIdLists(@Param("status") ArticleStatus status,
                                                   @Param("idList") List<String> idList);}
