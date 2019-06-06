package com.gz.tzreport.controller;

import com.alibaba.fastjson.JSON;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.*;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SumaryController {

    @Autowired
    private UsersServiceInterface usersServiceInterface;
    @Autowired
    private ArticleServiceInterface articleServiceInterface;
    @Autowired
    private CategoryServiceInterface categoryServiceInterface;
    @Autowired
    private TbplatformServiceInterface tbplatformServiceInterface;
    @Autowired
    private AdviceServiceInterface adviceServiceInterface;

    @RequestMapping("/allsummary")
    public JsonDTO getAllSummary(){
        JsonDTO jsonDTO = new JsonDTO();

        ArrayList<HashMap> list = new ArrayList<>();
        Integer usernum = usersServiceInterface.selectAll().size();
        HashMap userMap = new HashMap();
        userMap.put("number",usernum);
        userMap.put("title","用户总数");
        list.add(userMap);

        Integer articlenum = articleServiceInterface.selectAll().size();
        HashMap articleMap = new HashMap();
        articleMap.put("number",articlenum);
        articleMap.put("title","文章总数");
        list.add(articleMap);

        Integer categorynum = categoryServiceInterface.selectAll().size();
        HashMap categoryMap = new HashMap();
        categoryMap.put("number",categorynum);
        categoryMap.put("title","分类总数");
        list.add(categoryMap);

        Integer platformnum = tbplatformServiceInterface.selectAll().size();
        HashMap platformMap = new HashMap();
        platformMap.put("number",platformnum);
        platformMap.put("title","平台总数");
        list.add(platformMap);

        Integer advicenum = adviceServiceInterface.selectAll().size();
        HashMap adviceMap = new HashMap();
        adviceMap.put("number",advicenum);
        adviceMap.put("title","建议总数");
        list.add(adviceMap);

        jsonDTO.setJsonDTO(true, ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),list);
        return jsonDTO;
    }


}
