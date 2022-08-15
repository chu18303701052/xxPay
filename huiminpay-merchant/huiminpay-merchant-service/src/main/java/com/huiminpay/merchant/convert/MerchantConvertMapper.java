package com.huiminpay.merchant.convert;

import com.huiminpay.merchant.entity.Merchant;
import com.yh.merchant.dto.MerchantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MerchantConvertMapper {
  MerchantConvertMapper INSTANCE= Mappers.getMapper(MerchantConvertMapper.class);
  public Merchant dto2entity(MerchantDTO merchantDTO);
  public MerchantDTO entityToDto(Merchant merchant);

  public List<MerchantDTO> entityList2DTOList(List<Merchant> list);


}
