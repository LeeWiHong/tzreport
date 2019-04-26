package com.gz.tzreport.controller;


import com.gz.tzreport.service.ArticleServiceInterface;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleServiceInterface articleService;

    @RequestMapping("/addarticle")
    public JsonDTO AddArticle(){
        JsonDTO jsonDTO = new JsonDTO();

        return jsonDTO;
    }
    @RequestMapping("/allarticle")
    public JsonDTO AllArticle(int pageno,int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        return jsonDTO;
    }

}
