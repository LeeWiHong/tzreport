package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbCollection;

import java.util.List;

public interface CollectionServiceInterface {


    int deleteByPrimaryKey(Integer collectionId);


    int insert(TbCollection record);


    TbCollection selectByPrimaryKey(Integer collectionId);


    List<TbCollection> selectAll();


    int updateByPrimaryKey(TbCollection record);
}
