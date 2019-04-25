package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbPlatform;

import java.util.List;

public interface TbplatformServiceInterface {


    int deleteByPrimaryKey(Integer platformId);

    int insert(TbPlatform record);

    TbPlatform selectByPrimaryKey(Integer platformId);

    List<TbPlatform> selectAll();

    int updateByPrimaryKey(TbPlatform record);
}
