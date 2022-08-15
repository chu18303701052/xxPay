package com.huiminpay.merchant.controller;

import com.huiminpay.merchant.service.MerchantAppService;
import com.huiminpay.merchant.vo.MerchantRegisterVO;
import com.yh.merchant.api.MerchantService;
import com.yh.merchant.dto.MerchantDTO;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "商户controller,提供商户注册查询等功能tags",description = "controller")
@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @Reference
    private MerchantService merchantService;
    @Autowired
    private MerchantAppService merchantAppService;

    @GetMapping("/get/{id}")
    @ApiOperation("根据id查询商户信息")//方法描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商户id", dataType = "Long", paramType = "path")
    })
    public MerchantDTO findMerchantById(@PathVariable("id") Long id) {
        MerchantDTO merchant = merchantService.findMerchantById(id);
        return merchant;
    }

    @ApiOperation("获取验证码")
    @GetMapping("/sms/code")
    @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String", paramType = "query")
    public String getSmsCode(@RequestParam("phone") String phone) {
        String smsCode = merchantAppService.getSmsCode(phone);
        return smsCode;
    }

    @ApiOperation("商户注册")
    @GetMapping("/register")
    @ApiParam(name = "merchantRegisterVO", value = "商户注册的信息")
    public MerchantRegisterVO registerMerchant( MerchantRegisterVO merchantRegisterVO) {
//        String a="22q";
//        System.out.println(Integer.parseInt(a));
        return merchantAppService.registerMerchant(merchantRegisterVO);
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ApiParam(name = "file", value = "上传文件信息")
    public String upload(MultipartFile file){

        return  merchantAppService.upload(file);

    }
    @ApiOperation("资质申请")
    @PostMapping("/my/merchants/save")
    @ApiParam(name = "merchantDTO", value = "商户资质申请的信息")
    public void applyMerchant(@RequestBody MerchantDTO merchantDTO){
        //Long merchantId1 = SecurityUtil.getMerchantId();

        //从门票中解析商户id
       // Long merchantId=1556804954369323010L;
        //Long merchantId = SecurityUtil.getMerchantId();
        //从门票中解析商户Id
        Long merchantId = 1556816850994683906L;
        merchantService.applyMerchant(merchantId,merchantDTO);










    }

}




