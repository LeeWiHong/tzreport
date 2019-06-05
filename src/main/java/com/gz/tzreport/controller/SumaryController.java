package com.gz.tzreport.controller;

import com.alibaba.fastjson.JSON;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.*;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        HashMap hashMap = new HashMap();
        Integer usernum = usersServiceInterface.selectAll().size();
        Integer articlenum = articleServiceInterface.selectAll().size();
        Integer categorynum = categoryServiceInterface.selectAll().size();
        Integer platformnum = tbplatformServiceInterface.selectAll().size();
        Integer advicenum = adviceServiceInterface.selectAll().size();
        hashMap.put("usernum",usernum);
        hashMap.put("articlenum",articlenum);
        hashMap.put("categorynum",categorynum);
        hashMap.put("platformnum",platformnum);
        hashMap.put("advicenum",advicenum);
        jsonDTO.setJsonDTO(true, ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),hashMap);
        return jsonDTO;
    }

    @RequestMapping("/sum")
    public String test333(){
        return "lwwwwfd";
    }

}
