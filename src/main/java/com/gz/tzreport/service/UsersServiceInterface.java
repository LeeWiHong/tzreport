package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbUsers;

import java.util.List;

public interface UsersServiceInterface {

    int deleteByPrimaryKey(Integer userId);

    int insert(TbUsers record);

    TbUsers selectByPrimaryKey(Integer userId);

    List<TbUsers> selectAll();

    int updateByPrimaryKey(TbUsers record);

    List<TbUsers> selectByTelephone(String telephone);



}
