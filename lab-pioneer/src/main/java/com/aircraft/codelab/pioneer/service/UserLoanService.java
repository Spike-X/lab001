package com.aircraft.codelab.pioneer.service;

import com.aircraft.codelab.pioneer.pojo.dto.RealUserContractDto;
import com.aircraft.codelab.pioneer.pojo.dto.UserContractDto;

import java.util.List;

/**
 * 2021-11-26
 *
 * @author tao.zhang
 * @since 1.0
 */
public interface UserLoanService {
    List<UserContractDto> userLoanContractList(Long userId, Integer taskState);

    RealUserContractDto realUserLoanContractList(Long userId, Integer taskState);
}
