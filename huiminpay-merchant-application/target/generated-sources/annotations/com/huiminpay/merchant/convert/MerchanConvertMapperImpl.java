package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.vo.MerchantRegisterVO;
import com.yh.merchant.dto.MerchantDTO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-11T19:22:04+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class MerchanConvertMapperImpl implements MerchanConvertMapper {

    @Override
    public MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVo) {
        if ( merchantRegisterVo == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setUsername( merchantRegisterVo.getUsername() );
        merchantDTO.setMobile( merchantRegisterVo.getMobile() );

        return merchantDTO;
    }
}
