package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.gz.tzreport.pojo.TbAdvice;


public interface AdviceServiceInterface {


    int deleteByPrimaryKey(Integer adviceId);

    int insert(TbAdvice record);

    TbAdvice selectByPrimaryKey(Integer adviceId);

    Page<TbAdvice> selectAll();

    int updateByPrimaryKey(TbAdvice record);
}
