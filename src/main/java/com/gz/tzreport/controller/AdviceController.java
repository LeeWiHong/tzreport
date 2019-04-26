package com.gz.tzreport.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.annotation.UserLoginToken;
import com.gz.tzreport.dao.TbUsersMapper;
import com.gz.tzreport.pojo.TbAdvice;
import com.gz.tzreport.pojo.TbUsers;
import com.gz.tzreport.service.AdviceServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController

@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    private AdviceServiceInterface adviceServiceInterface;

    @Autowired
    private TbUsersMapper tbUsersMapper;

    @UserLoginToken
    @RequestMapping("/addadvice")
    public JsonDTO AddAdvice(@RequestParam(value = "userid") int userid, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content, @RequestParam(value = "description") String description){
        JsonDTO jsonDTO = new JsonDTO();
//        1.查询用户id是否存在数据
        TbUsers tbUsers = tbUsersMapper.selectByPrimaryKey(userid);
        if (tbUsers !=  null){
            TbAdvice tbAdvice = new TbAdvice();
            tbAdvice.setAdviceBody(content);
            tbAdvice.setAdviceDescript(description);
            tbAdvice.setAdviceTitle(title);
            tbAdvice.setUserId(userid);
            if (adviceServiceInterface.insert(tbAdvice) > 0){
                jsonDTO.setJsonDTO(true, ExceptionEnum.ADD_DATA_SUCCESS.getMsgcode(),ExceptionEnum.ADD_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
            }
            else {
                jsonDTO.setJsonDTO(false, ExceptionEnum.ADD_DATA_FAILURE.getMsgcode(), ExceptionEnum.ADD_DATA_FAILURE.getMsgdesc(), new ArrayList<>());
            }
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgcode(),ExceptionEnum.LOGIN_USER_NOTEXISTED.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/deladvice")
    public JsonDTO deleteAdvice(int adviceid){
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

    @RequestMapping("/alladvice")
    public JsonDTO AllDdvice(@RequestParam(defaultValue = "1") int pageNo,@RequestParam(defaultValue = "10") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageNo,pagesize);
        PageInfo<TbAdvice>pageInfo = new PageInfo<>(adviceServiceInterface.selectAll());
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),pageInfo);
        return jsonDTO;
    }

}
