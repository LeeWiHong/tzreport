package com.gz.tzreport.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.annotation.SuperAuthorityToken;
import com.gz.tzreport.annotation.UserLoginToken;
import com.gz.tzreport.dao.TbUsersMapper;
import com.gz.tzreport.pojo.TbAdvice;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.AdviceServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import com.gz.tzreport.uitls.ValidatorFormatCheckTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController

@RequestMapping("/api")
public class AdviceController {

    @Autowired
    private AdviceServiceInterface adviceServiceInterface;

    @RequestMapping("/addadvice")
    public JsonDTO AddAdvice(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content, @RequestParam(value = "telephone") String telephone){
        JsonDTO jsonDTO = new JsonDTO();
        if (ValidatorFormatCheckTools.isPhoneLegal(telephone)){
//           建议内容不能少于10个字符
            if (content.length() > 10)
            {
                TbAdvice tbAdvice = new TbAdvice();
                tbAdvice.setAdviceTitle(title);
                tbAdvice.setAdviceBody(content);
                tbAdvice.setAdviceTelephone(telephone);
                if (adviceServiceInterface.insert(tbAdvice) > 0){
                    jsonDTO.setJsonDTO(true,ExceptionEnum.ADD_DATA_SUCCESS.getMsgcode(),ExceptionEnum.ADD_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
                }
                else {
                    jsonDTO.setJsonDTO(false,ExceptionEnum.ADD_DATA_FAILURE.getMsgcode(),ExceptionEnum.ADD_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
                }
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.ADD_DATA_FAILURE.getMsgcode(),ExceptionEnum.ADD_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
            }

        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgcode(),ExceptionEnum.MOB_TELEPHONE_FORMAT_ERROR.getMsgdesc(),new ArrayList<>());
        }

        return jsonDTO;
    }

    @SuperAuthorityToken
    @RequestMapping("/deladvice")
    public JsonDTO deleteAdvice(@RequestParam(value = "adviceId") int adviceid){
        JsonDTO jsonDTO = new JsonDTO();
        TbAdvice tbAdvice = adviceServiceInterface.selectByPrimaryKey(adviceid);
        if (tbAdvice != null){
             if (adviceServiceInterface.deleteByPrimaryKey(adviceid) > 0){
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

    @SuperAuthorityToken
    @RequestMapping("/alladvice")
    public JsonDTO AllDdvice(@RequestParam(value = "pageno") int pageNo,@RequestParam(value = "pagesize") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageNo,pagesize);
        PageInfo<TbAdvice>pageInfo = new PageInfo<>(adviceServiceInterface.selectAll());
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }

}
