package com.gz.tzreport.service;

import com.gz.tzreport.dao.TbRolesMapper;
import com.gz.tzreport.pojo.TbRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbrolesServiceImp implements TbrolesServiceInterface {

    @Autowired
    private TbRolesMapper tbRolesMapper;
    @Override
    public int deleteByPrimaryKey(Integer roleId) {
        return tbRolesMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(TbRoles record) {
        return tbRolesMapper.insert(record);
    }

    @Override
    public TbRoles selectByPrimaryKey(Integer roleId) {
        return tbRolesMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<TbRoles> selectAll() {
        return tbRolesMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbRoles record) {
        return tbRolesMapper.updateByPrimaryKey(record);
    }

    @Override
    public TbRoles selectByRoleLeve(String leve) {
        return tbRolesMapper.selectByRoleLeve(leve);
    }
}
