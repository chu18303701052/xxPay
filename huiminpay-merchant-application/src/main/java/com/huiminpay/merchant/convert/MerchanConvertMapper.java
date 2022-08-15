package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.vo.MerchantRegisterVO;
import com.yh.merchant.dto.MerchantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MerchanConvertMapper {

    MerchanConvertMapper INSTANCE= Mappers.getMapper(MerchanConvertMapper.class);

    public MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVo);


}
