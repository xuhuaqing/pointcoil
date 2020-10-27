package com.pointcoil.controller.common;

import cn.hutool.json.JSONObject;
import com.pointcoil.conf.PointCoilProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.pointcoil.constant.Constants.*;
import static com.pointcoil.constant.MsgConstant.MSG_000000;


/**
 * @author:xiaoxi
 * @date:2019/5/28
 * @ver:1.0
 **/
@Slf4j
@RestController
@RequestMapping("/admin/adminUpload")
public class AdminUploadController {



    @Autowired
    private PointCoilProperties pointCoilProperties;
    /**
     * 上传文件到数据库
     *
     * @param
     * @return com.zeus.common.Response
     * @methodName uploadContract
     * @author wushuang
     * @date 2019/1/29
     */
    @RequestMapping(value = "upload", headers = "content-type=multipart/*", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String userUploadAndSave(@RequestParam("file") MultipartFile[] businessHeadFile) throws IOException {
        log.info("传过来的图片路径:{}", businessHeadFile);
        JSONObject jsonObjectOut = new JSONObject();
        String path = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();
      //  String realPath = REALPATH;
        FileInputStream fis = null;
        String headNewName = null;
        StringBuffer sb = new StringBuffer();
        StringBuilder sbs = new StringBuilder();
        Map map = new HashMap(EIGHT_NUM);
        for (int i = 0; i < businessHeadFile.length; i++) {
            if (businessHeadFile[i] != null) {
                //获取到文件的名
                String headName = businessHeadFile[i].getOriginalFilename();
                String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
                headNewName = UUID.randomUUID() + headLastName;
                File file3 = new File(path + File.separator + headNewName);

                try {
                    businessHeadFile[i].transferTo(file3);
                    if (sbs.length() > 0) {
                        sbs.append(",");
                    }
                    sb.append(iPurl + file3.toString());
                    sbs.append(iPurl  + headNewName);

                    map.put("src", sbs.toString());
                } catch (Exception e) {
                    jsonObjectOut.put("code", ERROR_CODE);
                    jsonObjectOut.put("msg", "上传失败");
                    return jsonObjectOut.toString();
                }
            } else {
                jsonObjectOut.put("code", ERROR_CODE);
                jsonObjectOut.put("msg", NO_DATA_MSG);
                return jsonObjectOut.toString();
            }
        }
        jsonObjectOut.put("code", SUCCESS_ADMIN_CODE);
        jsonObjectOut.put("msg", SUCCESS_MSG);
        jsonObjectOut.put("data", map);
        return jsonObjectOut.toString();
    }


  /*  *
     * 上传文件到数据库
     *
     * @param
     * @return com.zeus.common.Response
     * @methodName uploadContract
     * @author xiaoxi
     * @date 2019/1/29
     */
    @RequestMapping(value = "mapUpload", headers = "content-type=multipart/*", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile[] businessHeadFile) throws IOException {
        log.info("传过来的图片路径:{}", businessHeadFile);
        JSONObject jsonObjectOut = new JSONObject();
        String path = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();
        //String realPath = REALPATH;
        FileInputStream fis = null;
        String headNewName = null;
        StringBuffer sb = new StringBuffer();
        StringBuilder sbs = new StringBuilder();
        for (int i = 0; i < businessHeadFile.length; i++) {
            if (businessHeadFile[i] != null) {
                //获取到文件的名
                String headName = businessHeadFile[i].getOriginalFilename();
                String headLastName = headName.substring(headName.lastIndexOf("."), headName.length());
                headNewName = UUID.randomUUID() + headLastName;
                File file3 = new File(path + File.separator + headNewName);

                try {
                    businessHeadFile[i].transferTo(file3);
                    if (sbs.length() > 0) {
                        sbs.append(",");
                    }
                    sb.append(iPurl + file3.toString());
                    sbs.append(iPurl  + headNewName);
                } catch (Exception e) {
                    jsonObjectOut.put("code", ERROR_CODE);
                    jsonObjectOut.put("msg", "上传失败");
                    return jsonObjectOut.toString();
                }
            } else {
                jsonObjectOut.put("code", ERROR_CODE);
                jsonObjectOut.put("msg", NO_DATA_MSG);
                return jsonObjectOut.toString();
            }
        }
        File file = new File(path + "/" + headNewName);
        try {
            fis = new FileInputStream(String.valueOf(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObjectOut.put("code", MSG_000000);
        jsonObjectOut.put("msg", SUCCESS_MSG);
        jsonObjectOut.put("data", sbs.toString());
        return jsonObjectOut.toString();
    }


    /** 上传图片方法
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("uploadByCkedtior")
    public String uploadEditorImage(HttpServletResponse response, HttpServletRequest request, @RequestParam("upload")MultipartFile file) {
        JSONObject result = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //服务器上使用
        // String rootPath =request.getServletContext().getRealPath("/resource/uploads/");//target的目录
        //本地使用
        //String rootPath ="D:\\mall";
        String rootPath = pointCoilProperties.getImageUploadUrl();
        String iPurl = pointCoilProperties.getUrl();
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        String newFileName = res+originalFilename.substring(originalFilename.lastIndexOf("."));
        //新文件
        File newFile = new File(rootPath+ File.separator+newFileName);
        //判断目标文件所在的目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        //将内存中的数据写入磁盘
        try {
            file.transferTo(newFile);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        result.put("uploaded", 1);
        result.put("fileName", newFileName);
        result.put("url", iPurl+newFileName);
        return result.toJSONString(10);
    }

}
