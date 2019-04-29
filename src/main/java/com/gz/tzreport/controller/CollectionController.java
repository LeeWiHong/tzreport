package com.gz.tzreport.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.pojo.TbArticle;
import com.gz.tzreport.pojo.TbCollection;
import com.gz.tzreport.service.ArticleServiceInterface;
import com.gz.tzreport.service.CollectionServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController

@RequestMapping("/api")
public class CollectionController {

    @Autowired
    private CollectionServiceInterface collectionService;

    @Autowired
    private ArticleServiceInterface articleService;

    @RequestMapping("/addcollection")
    public JsonDTO AddCollection(Integer userid,Integer articleid){
        JsonDTO jsonDTO = new JsonDTO();
        TbArticle tbArticle = articleService.selectByPrimaryKey(articleid);
        if (tbArticle != null){
            TbCollection tbCollection = new TbCollection();
            tbCollection.setCollectionUserid(userid);
            tbCollection.setArticleBody(tbArticle.getArticleBody());
            tbCollection.setArticleDescript(tbArticle.getArticleDescript());
            tbCollection.setArticleId(tbArticle.getArticleId());
            tbCollection.setArticleImage(tbArticle.getArticleImage());
            tbCollection.setArticleLink(tbArticle.getArticleLink());
            tbCollection.setArticleTitle(tbArticle.getArticleTitle());
            if (collectionService.insert(tbCollection) > 0){
                jsonDTO.setJsonDTO(true,ExceptionEnum.INSERT_DATA_SUCCESS.getMsgcode(),ExceptionEnum.INSERT_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.INSERT_DATA_FAILURE.getMsgcode(),ExceptionEnum.INSERT_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
            }

        }
        else {
            jsonDTO.setJsonDTO(false, ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }

        return jsonDTO;
    }

    @RequestMapping("/delcollection")
    public JsonDTO deleteCollection(Integer collectionid){
        JsonDTO jsonDTO = new JsonDTO();
        TbCollection tbCollection = collectionService.selectByPrimaryKey(collectionid);
        if (tbCollection != null){
            if (collectionService.deleteByPrimaryKey(collectionid) > 0){
                jsonDTO.setJsonDTO(true,ExceptionEnum.DELETE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.DELETE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.DELETE_DATA_FAILURE.getMsgcode(),ExceptionEnum.DELETE_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
            }
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/allcollection")
    public JsonDTO AllCollection(Integer pageno,Integer pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageno,pagesize);
        PageInfo<TbCollection> pageInfo = new PageInfo<>(collectionService.selectAll());
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }

}
