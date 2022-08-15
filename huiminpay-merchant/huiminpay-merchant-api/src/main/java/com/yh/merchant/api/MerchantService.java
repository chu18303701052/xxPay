package com.yh.merchant.api;

import com.yh.merchant.dto.MerchantDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MerchantService {
    public MerchantDTO findMerchantById(Long id);

    public  MerchantDTO registerMerchant(MerchantDTO merchantDTO);

    public Boolean existMerchantByMobile(String mobile);

    String upload(MultipartFile file);

    public void applyMerchant(Long merchantId,MerchantDTO merchantDTO);
}
