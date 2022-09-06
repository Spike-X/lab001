package com.aircraft.codelab.pioneer.controller;

import com.aircraft.codelab.core.entities.CommonResult;
import com.aircraft.codelab.core.enums.ResultCode;
import com.aircraft.codelab.pioneer.pojo.dto.RealUserContractDto;
import com.aircraft.codelab.pioneer.pojo.dto.UserContractDto;
import com.aircraft.codelab.pioneer.service.UserLoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@RestController
@Api(tags = "借贷")
@RequestMapping("/loan")
public class UserLoanController {
    @Resource
    private UserLoanService userLoanService;

    @ApiOperation(value = "用户借贷合同列表1")
    @GetMapping(value = "/getUserLoanContractList", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<List<UserContractDto>> get(@RequestParam String userId) {
        log.debug("userId: {}", userId);
        List<UserContractDto> contractDtoList = userLoanService.userLoanContractList(Long.valueOf(userId), 6);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), contractDtoList);
    }

    @ApiOperation(value = "用户借贷合同列表2")
    @GetMapping(value = "/getRealUserLoanContractList", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResult<RealUserContractDto> getReal(@RequestParam String userId) {
        log.debug("userId: {}", userId);
        RealUserContractDto realUserContractDtoList = userLoanService.realUserLoanContractList(Long.valueOf(userId), 0);
        return CommonResult.success(ResultCode.SUCCESS.getMessage(), realUserContractDtoList);
    }
}
