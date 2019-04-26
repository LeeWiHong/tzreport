package com.gz.tzreport.service;

import com.github.pagehelper.Page;
import com.gz.tzreport.dao.TbAdviceMapper;
import com.gz.tzreport.pojo.TbAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdviceServiceImp implements AdviceServiceInterface {

    @Autowired
    private TbAdviceMapper tbAdviceMapper;
    @Override
    public int deleteByPrimaryKey(Integer adviceId) {
        return tbAdviceMapper.deleteByPrimaryKey(adviceId);
    }

    @Override
    public int insert(TbAdvice record) {
        return tbAdviceMapper.insert(record);
    }

    @Override
    public TbAdvice selectByPrimaryKey(Integer adviceId) {
        return tbAdviceMapper.selectByPrimaryKey(adviceId);
    }

    @Override
    public Page<TbAdvice> selectAll() {
        return tbAdviceMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbAdvice record) {
        return tbAdviceMapper.updateByPrimaryKey(record);
    }
}
