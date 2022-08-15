package com.huiminpay;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huiminpay.MerchantBootstrap;
import com.huiminpay.common.cache.util.EncryptUtil;
import com.yh.merchant.api.MerchantService;
import com.yh.merchant.dto.MerchantDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MerchantBootstrap.class)
public class TokenTemp {
    @Autowired
    MerchantService merchantService;

    @Test
    public void createTestToken() {
        Long merchantId = 1556954431952535554L;//填写用于测试的商户id
        MerchantDTO merchantDTO = merchantService.findMerchantById(merchantId);
        JSONObject token = new JSONObject();
        token.put("mobile", merchantDTO.getMobile());
        token.put("user_name", merchantDTO.getUsername());
        token.put("merchantId", merchantId);
        System.out.println("================================");
        String jwt_token = "Bearer " + EncryptUtil.encodeBase64(JSON.toJSONString(token).getBytes());
        System.out.println(jwt_token);
        //Bearer eyJtZXJjaGFudElkIjoxNTU2NTMyNTk4Mzk4Nzk5ODc0LCJ1c2VyX25hbWUiOiIxMTEiLCJtb2JpbGUiOiIxMTEifQ==
    }
}
