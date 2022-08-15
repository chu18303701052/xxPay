package com.huipinpay;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.qiniu.storage.Configuration;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestQiniu {


    @Test
    public void testUpload(){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huabei());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "x-CSlMd2TZwqTjPqZvzKD_qN32Jy0cx1COCBKA9C";
        String secretKey = "kMDh6Tj0uUUylnfj1Wpi-vKOcTlx9rf5hCUiodsT";
        String bucket = "huiminpay-yzf";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\12719\\Pictures\\Saved Pictures\\5.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        //认证
        Auth auth = Auth.create(accessKey, secretKey);
        //获取令牌
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }
}
