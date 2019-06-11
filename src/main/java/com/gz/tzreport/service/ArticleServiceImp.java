package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.gz.tzreport.dao.TbArticleMapper;
import com.gz.tzreport.pojo.TbArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleServiceInterface {

    @Autowired
    private TbArticleMapper tbArticleMapper;
    @Override
    public int deleteByPrimaryKey(Integer articleId) {
        return tbArticleMapper.deleteByPrimaryKey(articleId);
    }

    @Override
    public int insert(TbArticle record) {
        return tbArticleMapper.insert(record);
    }

    @Override
    public TbArticle selectByPrimaryKey(Integer articleId) {
        return tbArticleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public Page<TbArticle> selectAll() {
        return tbArticleMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbArticle record) {
        return tbArticleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TbArticle> selectIndexArticle() {
        return tbArticleMapper.selectIndexArticle();
    }
}
