package com.gz.tzreport.service;

import com.gz.tzreport.pojo.TbRoles;

import java.util.List;

public interface TbrolesServiceInterface {


    int deleteByPrimaryKey(Integer roleId);


    int insert(TbRoles record);


    TbRoles selectByPrimaryKey(Integer roleId);

    List<TbRoles> selectAll();


    int updateByPrimaryKey(TbRoles record);

    TbRoles selectByRoleLeve(String leve);
}
