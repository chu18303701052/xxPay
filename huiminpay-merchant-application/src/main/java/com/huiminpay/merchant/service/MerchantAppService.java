package com.huiminpay.merchant.service;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.BusinessException;
import com.huiminpay.common.cache.exception.ExceptionCast;
import com.huiminpay.common.cache.util.PhoneUtil;
import com.huiminpay.common.cache.util.QiniuUtil;
import com.huiminpay.merchant.convert.MerchanConvertMapper;
import com.huiminpay.merchant.vo.MerchantRegisterVO;
import com.yh.merchant.api.MerchantService;
import com.yh.merchant.dto.MerchantDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class MerchantAppService {

    @Value("${sms.effectiveTime}")
    private Integer effectiveTime;

    @Value("${sms.type.name}")
    private String typeName;

    @Value("${oss.qiniu.accessKey}")
    private String accessKey;

    @Value("${oss.qiniu.secretKey}")
    private String secretKey;

    @Value("${oss.qiniu.bucket}")
    private String bucket;

    @Value("${oss.qiniu.domain}")
    private String domain;


    @Autowired
    private RestTemplate restTemplate;

    @Reference
    private MerchantService merchantService;

    public String getSmsCode(String phone){
        HttpHeaders tempHeaders = new HttpHeaders();
        tempHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map body = new HashMap();
        body.put("mobile",phone);
        HttpEntity httpEntity = new HttpEntity(body, tempHeaders);

        //远程调用验证码服务
        //http://localhost:56085/sailing/verify?name=sms&verificationCode=351360&verificationKey=sms%3Aaa4a6ebcf3094f3483971fb892834812
        ResponseEntity<Map> responseEntity = restTemplate
                .exchange("http://localhost:56085/sailing/generate?effectiveTime="
                        + effectiveTime + "&name=" + typeName, HttpMethod.POST, httpEntity, Map.class);

        Map map = responseEntity.getBody();

        Map resMap = (Map)map.get("result");
        String key = resMap.get("key").toString();
        return key;
    }

    public MerchantRegisterVO registerMerchant(MerchantRegisterVO merchantRegisterVO) {
        //参数不能为空
        if(merchantRegisterVO==null || StringUtils.isEmpty(merchantRegisterVO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_110006);
        }
        //手机号格式不正确
        if(!PhoneUtil.isMatches(merchantRegisterVO.getMobile())){
            ExceptionCast.cast(CommonErrorCode.E_100109);
        }
        //手机号已存在 在service中做了判断
        Boolean flag = merchantService.existMerchantByMobile(merchantRegisterVO.getMobile());
         if(flag){
             ExceptionCast.cast(CommonErrorCode.E_100113);
         }
        //1、校验验证码http://localhost:56085/sailing/verify?name=sms&verificationCode=351360&verificationKey=sms%3Aaa4a6ebcf3094f3483971fb892834812
        ResponseEntity<Map> responseEntity = restTemplate
                .exchange("http://localhost:56085/sailing/verify?name="
                                + typeName + "&verificationCode=" + merchantRegisterVO.getVerifiyCode() +
                                "&verificationKey=" + merchantRegisterVO.getVerifiykey(),
                        HttpMethod.POST, HttpEntity.EMPTY, Map.class);
        //遍历结果
        Map body = responseEntity.getBody();
        Boolean res = (Boolean) body.get("result");

        //新版本抛异常
        if(!res){
            log.error("验证码错误!");
            //throw new BusinessException(CommonErrorCode.E_100102);

            //使用工具类处理异常
            ExceptionCast.cast(CommonErrorCode.E_100102);
        }
        //2、调用商户服务，注册商户信息
        MerchantDTO merchantDTO = MerchanConvertMapper.INSTANCE.vo2dto(merchantRegisterVO);
        merchantService.registerMerchant(merchantDTO);
        /*if(!res){
           /* MerchantDTO merchantDTO = new MerchantDTO();
            BeanUtils.copyProperties(merchantRegisterVO,merchantDTO);
            //2、调用商户服务，注册商户信息
            MerchantDTO merchantDTO = MerchanConvertMapper.INSTANCE.vo2dto(merchantRegisterVO);
            merchantService.registerMerchant(merchantDTO);
        }else{
            log.error("验证码错误!");
            throw new BusinessException(CommonErrorCode.E_100102);
            //throw new RuntimeException("验证码错误！");
        }*/

        return merchantRegisterVO;
    }
    public String upload(MultipartFile file) {

        String key = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();//文件原始名称
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        key = key + ext;
        try {
            byte[] bytes = file.getBytes();
            QiniuUtil.upload(accessKey, secretKey, bucket, key, bytes);
        } catch (IOException e) {
            log.error("上传异常：{}", e.getMessage());
            throw new BusinessException(CommonErrorCode.E_100106);


        }
        return domain+key;

    }}