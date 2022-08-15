package com.huiminpay.merchant.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.exception.ExceptionCast;


import com.huiminpay.merchant.convert.MerchantConvertMapper;
import com.huiminpay.merchant.entity.Merchant;
import com.huiminpay.merchant.mapper.MerchantMapper;
import com.yh.merchant.api.MerchantService;
import com.yh.merchant.dto.MerchantDTO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public MerchantDTO findMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setId(merchant.getId());
        BeanUtils.copyProperties(merchant,merchantDTO);
        return merchantDTO;
    }

    @Override
    public MerchantDTO registerMerchant(MerchantDTO merchantDTO) {
//        String s = JSON.toJSONString(merchantDTO);
//        Merchant merchant1 = JSON.parseObject(s, Merchant.class);


        merchantDTO.setAuditStatus("0");//商户状态;0未申请，1已经申请，
       // Merchant merchant = new Merchant();
       // BeanUtils.copyProperties(merchantDTO,merchant);
        Merchant merchant = MerchantConvertMapper.INSTANCE.dto2entity(merchantDTO);

        merchantMapper.insert(merchant);
       return MerchantConvertMapper.INSTANCE.entityToDto(merchant);

//        return merchantDTO;
    }

    @Override
    public Boolean existMerchantByMobile(String mobile) {
        LambdaQueryWrapper<Merchant> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Merchant::getMobile,mobile);
        List<Merchant> merchants = merchantMapper.selectList(lambdaQueryWrapper);
        if(merchants!=null && merchants.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public String upload(MultipartFile file) {
        //把文件上传到七牛云

        return null;
    }

    @Override
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) {
        if(merchantDTO==null || merchantId==null){
            ExceptionCast.cast(CommonErrorCode.E_110006);


        }

        Merchant merchanDb = merchantMapper.selectById(merchantId);


        Merchant merchant = MerchantConvertMapper.INSTANCE.dto2entity(merchantDTO);
        merchant.setId(merchantId);
        merchant.setMobile(merchanDb.getMobile());
        //状态 更新
        merchant.setAuditStatus("1");
        merchantMapper.updateById(merchant);
    }



}