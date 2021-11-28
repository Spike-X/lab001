package com.aircraft.codelab.labcore.mapper;

import com.aircraft.codelab.labcore.pojo.dto.RealUserContractDto;
import com.aircraft.codelab.labcore.pojo.dto.UserContractDto;
import com.aircraft.codelab.labcore.pojo.entity.LoanContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoanContractMapper {
    LoanContract selectByPrimaryKey(Long id);

    List<UserContractDto> selectUserContractList(@Param("userId") Long userId, @Param("contractState") Integer contractState);

    List<UserContractDto> selectUserContract(@Param("userId") Long userId, @Param("contractState") Integer contractState);

    RealUserContractDto selectRealUserContract(@Param("userId") Long userId, @Param("contractState") Integer contractState);
}