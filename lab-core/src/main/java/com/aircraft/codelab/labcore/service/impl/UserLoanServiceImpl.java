package com.aircraft.codelab.labcore.service.impl;

import com.aircraft.codelab.labcore.mapper.LoanContractMapper;
import com.aircraft.codelab.labcore.pojo.dto.RealUserContractDto;
import com.aircraft.codelab.labcore.pojo.dto.UserContractDto;
import com.aircraft.codelab.labcore.service.UserLoanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
@Service
public class UserLoanServiceImpl implements UserLoanService {
    @Resource
    private LoanContractMapper loanContractMapper;

    @Override
    public List<UserContractDto> userLoanContractList(Long userId, Integer taskState) {
//        return loanContractMapper.selectUserContractList(userId, taskState);
        return loanContractMapper.selectUserContract(userId, taskState);
    }

    @Override
    public RealUserContractDto realUserLoanContractList(Long userId, Integer taskState) {
        return loanContractMapper.selectRealUserContract(userId, taskState);
    }
}
