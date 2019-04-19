package com.gz.tzreport.service;

import com.gz.tzreport.dao.TbCategoryMapper;
import com.gz.tzreport.pojo.TbCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryServiceInterface {

    @Autowired
    private TbCategoryMapper tbCategoryMapper;
    @Override
    public int deleteByPrimaryKey(Integer categoryId) {
        return tbCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    @Override
    public int insert(TbCategory record) {
        return tbCategoryMapper.insert(record);
    }

    @Override
    public TbCategory selectByPrimaryKey(Integer categoryId) {
        return tbCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<TbCategory> selectAll() {
        return tbCategoryMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbCategory record) {
        return tbCategoryMapper.updateByPrimaryKey(record);
    }
}
