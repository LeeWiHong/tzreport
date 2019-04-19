package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbCategory;

import java.util.List;

public interface CategoryServiceInterface {

    int deleteByPrimaryKey(Integer categoryId);

    int insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer categoryId);

    List<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
}
