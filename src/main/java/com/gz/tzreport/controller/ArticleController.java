package com.gz.tzreport.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.pojo.TbArticle;
import com.gz.tzreport.service.ArticleServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleServiceInterface articleService;

    @RequestMapping("/addarticle")
    public JsonDTO AddArticle(@RequestParam(value = "title") String title, @RequestParam(value = "link") String link, @RequestParam(value = "platform") Integer platform, @RequestParam(value = "category") Integer category, @RequestParam(value = "description") String description, @RequestParam(value = "body") String body, @RequestParam(value = "image") String image){
        JsonDTO jsonDTO = new JsonDTO();
        TbArticle tbArticle = new TbArticle();
        tbArticle.setArticleBody(body);
        tbArticle.setArticleCategory(category);
        tbArticle.setArticleDescript(description);
        tbArticle.setArticleImage(image);
        tbArticle.setArticleLink(link);
        tbArticle.setArticlePlatform(platform);
        tbArticle.setArticleTitle(title);
        if (articleService.insert(tbArticle) > 0){
            jsonDTO.setJsonDTO(true, ExceptionEnum.ADD_DATA_SUCCESS.getMsgcode(),ExceptionEnum.ADD_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.ADD_DATA_FAILURE.getMsgcode(),ExceptionEnum.ADD_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/delarticle")
    public JsonDTO deleteArticle(@RequestParam(value = "articleid") Integer articleid){
        JsonDTO jsonDTO = new JsonDTO();
        if (articleService.deleteByPrimaryKey(articleid) > 0){
            jsonDTO.setJsonDTO(true,ExceptionEnum.DELETE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.DELETE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.DELETE_DATA_FAILURE.getMsgcode(),ExceptionEnum.DELETE_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/updarticle")
    public JsonDTO updateArticle(@RequestParam(value = "articleid") Integer articleid,@RequestParam(value = "title") String title, @RequestParam(value = "link") String link, @RequestParam(value = "platform") Integer platform, @RequestParam(value = "category") Integer category, @RequestParam(value = "description") String description, @RequestParam(value = "body") String body, @RequestParam(value = "image") String image){
        JsonDTO jsonDTO = new JsonDTO();
        if (articleService.selectByPrimaryKey(articleid) != null){
            TbArticle tbArticle = new TbArticle();
            tbArticle.setArticleTitle(title);
            tbArticle.setArticlePlatform(platform);
            tbArticle.setArticleLink(link);
            tbArticle.setArticleImage(image);
            tbArticle.setArticleDescript(description);
            tbArticle.setArticleCategory(category);
            tbArticle.setArticleBody(body);
            if (articleService.updateByPrimaryKey(tbArticle) > 0){
                jsonDTO.setJsonDTO(true,ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.UPDATE_DATA_FAILURE.getMsgcode(),ExceptionEnum.UPDATE_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
            }
        }
        return jsonDTO;
    }
    @RequestMapping("/allarticle")
    public JsonDTO AllArticle(@RequestParam(value = "pageno") int pageno,@RequestParam(value = "pagesize") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageno,pagesize);
        PageInfo<TbArticle> pageInfo = new PageInfo<>(articleService.selectAll());
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }

    @RequestMapping("/indexarticle")
    public JsonDTO indexArticle(@RequestParam(value = "pageno") int pageno,@RequestParam(value = "pagesize") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageno,pagesize);
        PageInfo<TbArticle> pageInfo = new PageInfo<>(articleService.selectIndexArticle());

        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }


}
