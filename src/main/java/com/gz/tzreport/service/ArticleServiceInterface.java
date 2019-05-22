package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbArticle;

import java.util.List;

public interface ArticleServiceInterface {


    int deleteByPrimaryKey(Integer articleId);


    int insert(TbArticle record);


    TbArticle selectByPrimaryKey(Integer articleId);


    List<TbArticle> selectAll();

    int updateByPrimaryKey(TbArticle record);

    List<TbArticle> selectIndexArticle();
}
