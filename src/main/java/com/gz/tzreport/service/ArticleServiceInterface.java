package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.pojo.TbArticle;

import java.util.List;

public interface ArticleServiceInterface {


    int deleteByPrimaryKey(Integer articleId);


    int insert(TbArticle record);


    TbArticle selectByPrimaryKey(Integer articleId);


    Page<TbArticle> selectAll();

    int updateByPrimaryKey(TbArticle record);

    List<TbArticle> selectIndexArticle();
}
