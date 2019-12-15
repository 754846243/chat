package com.group.chat.utils;

/**
 * @author 陈雨菲
 * @description 七牛云上传工具类
 * @data 2019/12/10
 */

import com.google.gson.Gson;
import com.group.chat.config.QiniuConfigure;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class QiniuUpload {

    public static String upload(MultipartFile file, String fileName) throws IOException {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(QiniuConfigure.accessKey, QiniuConfigure.secretKey);
        String upToken = auth.uploadToken(QiniuConfigure.bucket);
        Response response = uploadManager.put(file.getBytes(), fileName, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return QiniuConfigure.path + putRet.key;
    }
}


