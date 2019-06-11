package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.gz.tzreport.pojo.TbCategory;

public interface CategoryServiceInterface {

    int deleteByPrimaryKey(Integer categoryId);

    int insert(TbCategory record);

    TbCategory selectByPrimaryKey(Integer categoryId);

    Page<TbCategory> selectAll();

    int updateByPrimaryKey(TbCategory record);
}
