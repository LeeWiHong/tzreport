package com.gz.tzreport.controller;


import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.IDUtils;
import com.gz.tzreport.uitls.JsonDTO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class FileUploadController {

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
//,consumes = "multipart/form-data"
    @RequestMapping(path = "/uploadimage",consumes = "multipart/form-data")

    public JsonDTO FileUpload(@RequestParam(value = "uploadimage") MultipartFile uploadimage){

        System.out.println("我是upload方法");
        System.out.println(uploadimage.getOriginalFilename());
        JsonDTO jsonDTO = new JsonDTO();

        String oldName = uploadimage.getOriginalFilename();
        String newName = IDUtils.genImageName();
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        String filePath = new DateTime().toString("/yyyy/MM/dd");
        String imageurl = baseUrl + filePath + "/" + newName;
        jsonDTO.setJsonDTO(true, ExceptionEnum.SYSTEN_FILE_UPLOAD_SUCCESS.getMsgcode(),ExceptionEnum.SYSTEN_FILE_UPLOAD_SUCCESS.getMsgdesc(),imageurl);

        return jsonDTO;
    }
}
