package com.gz.tzreport.controller;


import com.gz.tzreport.pojo.TbCategory;
import com.gz.tzreport.service.CategoryServiceInterface;
import com.gz.tzreport.uitls.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Value("${FTP.ADDRESS}")
    private String host;

    @Value("${FTP.PORT}")
    private int port;

    @Value("${FTP.USERNAME}")
    private String username;

    @Value("${FTP.PASSWORD}")
    private String password;

    @Value("${FTP.BASEPATH}")
    private String basePath;

    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;


    @Autowired
    private CategoryServiceInterface categoryServiceInterface;

    /**
    * @description: 返回所有分类回首页
    *
    * @return:
    **/

    @RequestMapping("/allcategory")
    public JsonDTO getAllCategory(){
        JsonDTO jsonDTO = new JsonDTO();
        List<TbCategory> list = categoryServiceInterface.selectAll();
        if (list.size() > 0){
            jsonDTO.setJsonDTO(true, ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),list);
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/incategory")
    public JsonDTO InsertCategory(@RequestParam("/name") String CategoryName, @RequestParam("image") MultipartFile uploadFile, @RequestParam("/description") String Description){
        JsonDTO jsonDTO = new JsonDTO();
        String oldName = uploadFile.getOriginalFilename();
        String newName = IDUtils.genImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        String filePath = new DateTime().toString("/yyyy/MM/dd");

        TbCategory tbCategory = new TbCategory();
        tbCategory.setCategoryName(CategoryName);
        tbCategory.setDescription(Description);
        tbCategory.setCategoryImage(baseUrl+filePath+"/"+newName);
        categoryServiceInterface.insert(tbCategory);

        try {
            InputStream inputStream = uploadFile.getInputStream();
            boolean result = FtpUtils.uploadFile(host,port,username,password,basePath,filePath,newName,inputStream);
            if (result){
                jsonDTO.setJsonDTO(true,ExceptionEnum.INSERT_DATA_SUCCESS.getMsgcode(),ExceptionEnum.INSERT_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
            }
            else {
                jsonDTO.setJsonDTO(false,ExceptionEnum.INSERT_DATA_FAILURE.getMsgcode(),ExceptionEnum.INSERT_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
            }
        }
        catch (IOException e){
            throw new CustomException(ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getHttpStatus(),ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getMsgcode(),ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getMsgdesc());
        }

        return jsonDTO;
    }

    @RequestMapping("/updatecategory")
    public JsonDTO updateCategory(@RequestParam("catid") int catid,@RequestParam("/name") String CategoryName, @RequestParam("image") MultipartFile uploadFile, @RequestParam("/description") String Description){
        JsonDTO jsonDTO = new JsonDTO();
        TbCategory tbCategory = categoryServiceInterface.selectByPrimaryKey(catid);
        if (tbCategory != null){
            tbCategory.setCategoryName(CategoryName);
            tbCategory.setDescription(Description);

            String oldName = uploadFile.getOriginalFilename();
            String newName = IDUtils.genImageName();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            String filePath = new DateTime().toString("/yyyy/MM/dd");

            tbCategory.setCategoryName(CategoryName);
            tbCategory.setDescription(Description);
            tbCategory.setCategoryImage(baseUrl+filePath+"/"+newName);
            categoryServiceInterface.insert(tbCategory);

            try {
                InputStream inputStream = uploadFile.getInputStream();
                boolean result = FtpUtils.uploadFile(host,port,username,password,basePath,filePath,newName,inputStream);
                if (result){
                    jsonDTO.setJsonDTO(true,ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
                }
                else {
                    jsonDTO.setJsonDTO(false,ExceptionEnum.UPDATE_DATA_FAILURE.getMsgcode(),ExceptionEnum.INSERT_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
                }
            }
            catch (IOException e){
                throw new CustomException(ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getHttpStatus(),ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getMsgcode(),ExceptionEnum.SYSTEN_FILE_UPLOAD_ERROR.getMsgdesc());
            }
        }else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }

        return jsonDTO;
    }

    @RequestMapping("/delcategory")
    public JsonDTO deleteCategory(@RequestParam("catid") int catid){
        JsonDTO jsonDTO = new JsonDTO();
        TbCategory tbCategory = categoryServiceInterface.selectByPrimaryKey(catid);
        if (tbCategory != null){
            categoryServiceInterface.deleteByPrimaryKey(tbCategory.getCategoryId());
            jsonDTO.setJsonDTO(false,ExceptionEnum.DELETE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.DELETE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }


}
