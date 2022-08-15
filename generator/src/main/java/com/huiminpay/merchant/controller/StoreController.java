package com.huiminpay.merchant.controller;


import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangzhenfang
 * @since 2022-08-08
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class StoreController {

    @Autowired
    private StoreDTOService storeDTOService;
}
