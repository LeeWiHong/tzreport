package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.gz.tzreport.pojo.TbPlatform;

import java.util.List;

public interface TbplatformServiceInterface {


    int deleteByPrimaryKey(Integer platformId);

    int insert(TbPlatform record);

    TbPlatform selectByPrimaryKey(Integer platformId);

    Page<TbPlatform> selectAll();

    int updateByPrimaryKey(TbPlatform record);
}
