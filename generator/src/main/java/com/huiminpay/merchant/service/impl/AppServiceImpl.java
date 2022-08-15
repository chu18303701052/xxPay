package com.huiminpay.merchant.service.impl;

import com.huiminpay.dto.AppDTO;
import com.huiminpay.merchant.mapper.AppMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huiminpay.merchant.service.IAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangzhenfang
 * @since 2022-08-08
 */
@Slf4j
@Service
@Transactional
public class AppServiceImpl extends ServiceImpl<AppMapper, AppDTO> implements IAppService {

}
