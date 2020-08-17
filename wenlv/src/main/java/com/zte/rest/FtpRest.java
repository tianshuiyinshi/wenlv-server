package com.zte.rest;

import com.alibaba.fastjson.JSONObject;
import com.zte.common.utils.DateUtil;
import com.zte.common.utils.FtpUtils;
import com.zte.common.utils.JsonResult;
import com.zte.common.utils.SystemUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author yinsiwei
 * @date 2020-08-04 10:19
 */

@Api(tags = "文件上传操作类")
@RestController
@RequestMapping("/ftp")
public class FtpRest {

    private static Logger log = LoggerFactory.getLogger(SystemUtils.class);
    @ApiOperation("文件上传接口")
    @PostMapping("/addFile")
    public JsonResult addFile(@RequestParam MultipartFile file){
        JsonResult result;
        if (!file.isEmpty()){
            String path;
            String contentType = file.getContentType();
            String extensionName = file.getOriginalFilename().split("\\.")[1];
            String newFilename = DateUtil.getTimestampOfDateTime(DateUtil.toLocalDateTime(new Date())).toString()+"."+extensionName;
            //使用日期+"/"作为文件夹路径
            String tmpPath = String.valueOf(new StringBuffer(DateUtil.getDBDatetime().substring(0,8)).insert(6,"/").insert(4,"/"));
            InputStream inputStream=null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            //图片类型文件和视频类型文件分开存储
            if (contentType.contains("image")){
                 path = FtpUtils.uploadFile("/image/" + tmpPath, newFilename, bufferedInputStream);
                 if (path.isEmpty()){
                     result = JsonResult.getFail("上传失败");
                 }else {
                     result = JsonResult.getSuccess(path);
                 }
            }else if (contentType.contains("video")){
                path = FtpUtils.uploadFile("/video/" + tmpPath, newFilename, bufferedInputStream);
                if (path.isEmpty()){
                    result = JsonResult.getFail("上传失败");
                }else {
                    result = JsonResult.getSuccess(path);
                }
            }else {
                result = JsonResult.getFail("文件类型错误");
            }
        }else {
            result = JsonResult.getFail("文件为空");
        }
        return result;
    }

    @ApiOperation("删除文件接口")
    @PostMapping("/deleteFile")
    public JsonResult deleteFile(@RequestBody JSONObject body){
        JsonResult result;
        if (body!=null&&body.containsKey("fullPath")){
            String fullPath = body.getString("fullPath");
            String[] resourcePaths = fullPath.split("/");
            String resourceName = resourcePaths[resourcePaths.length - 1];
            String resourcePath = fullPath.replaceAll(resourceName, "");
            FtpUtils.deleteFile(resourcePath,resourceName);
            result = JsonResult.getSuccess("success");
        }else {
            result = JsonResult.getFail("参数为空");
        }
        return result;
    }

}
