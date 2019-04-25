package com.gz.tzreport.service;

import com.gz.tzreport.dao.TbPlatformMapper;
import com.gz.tzreport.pojo.TbPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TbplatformServiceImp implements TbplatformServiceInterface {

    @Autowired
    private TbPlatformMapper tbPlatformMapper;
    @Override
    public int deleteByPrimaryKey(Integer platformId) {
        return tbPlatformMapper.deleteByPrimaryKey(platformId);
    }

    @Override
    public int insert(TbPlatform record) {
        return tbPlatformMapper.insert(record);
    }

    @Override
    public TbPlatform selectByPrimaryKey(Integer platformId) {
        return tbPlatformMapper.selectByPrimaryKey(platformId);
    }

    @Override
    public List<TbPlatform> selectAll() {
        return tbPlatformMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbPlatform record) {
        return tbPlatformMapper.updateByPrimaryKey(record);
    }
}
