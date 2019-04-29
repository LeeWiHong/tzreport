package com.gz.tzreport.service;

import com.gz.tzreport.dao.TbCollectionMapper;
import com.gz.tzreport.pojo.TbCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImp implements CollectionServiceInterface {

    @Autowired
    private TbCollectionMapper tbCollectionMapper;
    @Override
    public int deleteByPrimaryKey(Integer collectionId) {
        return tbCollectionMapper.deleteByPrimaryKey(collectionId);
    }

    @Override
    public int insert(TbCollection record) {
        return tbCollectionMapper.insert(record);
    }

    @Override
    public TbCollection selectByPrimaryKey(Integer collectionId) {
        return tbCollectionMapper.selectByPrimaryKey(collectionId);
    }

    @Override
    public List<TbCollection> selectAll() {
        return tbCollectionMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(TbCollection record) {
        return tbCollectionMapper.updateByPrimaryKey(record);
    }
}
