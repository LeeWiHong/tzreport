package com.gz.tzreport.controller;


import com.gz.tzreport.pojo.TbCategory;
import com.gz.tzreport.service.CategoryServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceInterface categoryServiceInterface;

    /**
    * @description: 返回所有分类回首页
    *
    * @return:
    **/

    @RequestMapping("/Allcategory")
    public JsonDTO getAllCategory(){
        JsonDTO jsonDTO = new JsonDTO();
        List<TbCategory> list = new ArrayList<>();
        list = categoryServiceInterface.selectAll();
        if (list.size() > 0){
            jsonDTO.setJsonDTO(true, ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),list);
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    public JsonDTO InsertCategory(String CategoryName,String Image,String Description){
        return null;
    }
}
