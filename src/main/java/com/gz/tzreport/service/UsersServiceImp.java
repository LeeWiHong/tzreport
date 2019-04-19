package com.gz.tzreport.service;

import com.gz.tzreport.dao.TbUsersMapper;
import com.gz.tzreport.pojo.TbUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImp implements UsersServiceInterface {

    @Autowired
    private TbUsersMapper tbUsersMapper;

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return tbUsersMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(TbUsers record) {
        return tbUsersMapper.insert(record);
    }

    @Override
    public TbUsers selectByPrimaryKey(Integer userId) {
        return tbUsersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<TbUsers> selectAll() {
        return tbUsersMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbUsers record) {
        return tbUsersMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<TbUsers> selectByTelephone(String telephone) {
        return tbUsersMapper.selectByTelephone(telephone);
    }

    @Override
    public int updateTokenByPrimaryKey(TbUsers tbUsers) {
        return tbUsersMapper.updateTokenByPrimaryKey(tbUsers);
    }
}
