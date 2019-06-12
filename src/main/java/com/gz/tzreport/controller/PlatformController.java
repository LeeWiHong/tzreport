package com.gz.tzreport.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gz.tzreport.annotation.SuperAuthorityToken;
import com.gz.tzreport.pojo.TbPlatform;
import com.gz.tzreport.service.TbplatformServiceInterface;
import com.gz.tzreport.uitls.ExceptionEnum;
import com.gz.tzreport.uitls.JsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PlatformController {

    @Autowired
    private TbplatformServiceInterface tbplatformService;

    @RequestMapping("/addplatform")
    public JsonDTO addPlatForm(@RequestParam(value = "platname") String platname, @RequestParam("platlink") String platlink, @RequestParam("platdescription") String platdescription){
        JsonDTO jsonDTO = new JsonDTO();
        TbPlatform tbPlatform = new TbPlatform();
        tbPlatform.setPlatformName(platname);
        tbPlatform.setPlatformDescript(platdescription);
        tbPlatform.setPlatformLink(platlink);
        if (tbplatformService.insert(tbPlatform) > 0){
            jsonDTO.setJsonDTO(true, ExceptionEnum.ADD_DATA_SUCCESS.getMsgcode(),ExceptionEnum.ADD_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.ADD_DATA_FAILURE.getMsgcode(),ExceptionEnum.ADD_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/delplatform")
    public JsonDTO deletePlatform(@RequestParam(value = "platid") int platid){
        JsonDTO jsonDTO = new JsonDTO();
        TbPlatform tbPlatform = tbplatformService.selectByPrimaryKey(platid);
        if (tbPlatform != null){
            tbplatformService.deleteByPrimaryKey(tbPlatform.getPlatformId());
            jsonDTO.setJsonDTO(true,ExceptionEnum.DELETE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.DELETE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/updateplatform")
    public JsonDTO updatePlatform(@RequestParam(value = "platid") int platid,@RequestParam(value = "platname") String platname, @RequestParam("/platlink") String platlink, @RequestParam("/platdesc") String platdescription){
        JsonDTO jsonDTO = new JsonDTO();
        TbPlatform tbPlatform = tbplatformService.selectByPrimaryKey(platid);
        if (tbPlatform != null){
            tbPlatform.setPlatformName(platname);
            tbPlatform.setPlatformDescript(platdescription);
            tbPlatform.setPlatformLink(platlink);
            tbplatformService.updateByPrimaryKey(tbPlatform);
            jsonDTO.setJsonDTO(true,ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgcode(),ExceptionEnum.UPDATE_DATA_SUCCESS.getMsgdesc(),new ArrayList<>());
        }
        else {
            jsonDTO.setJsonDTO(false,ExceptionEnum.QUERARY_DATA_FAILURE.getMsgcode(),ExceptionEnum.QUERARY_DATA_FAILURE.getMsgdesc(),new ArrayList<>());
        }
        return jsonDTO;
    }

    @RequestMapping("/allplatform")
    public JsonDTO AllPlatform(@RequestParam(value = "pageno") int pageNo,@RequestParam(value = "pagesize") int pagesize){
        JsonDTO jsonDTO = new JsonDTO();
        PageHelper.startPage(pageNo,pagesize);
        PageInfo<TbPlatform> list = new PageInfo<>(tbplatformService.selectAll()) ;
        jsonDTO.setJsonDTO(true,ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgcode(),ExceptionEnum.QUERARY_DATA_SUCCESS.getMsgdesc(),list);
        return jsonDTO;
    }
}
