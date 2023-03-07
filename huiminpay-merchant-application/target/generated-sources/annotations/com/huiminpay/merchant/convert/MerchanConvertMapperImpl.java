package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.vo.MerchantRegisterVO;
import com.yh.merchant.dto.MerchantDTO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-07T10:19:42+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.6 (JetBrains s.r.o)"
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
