package com.aircraft.codelab.pioneer.mapper;
import java.util.Collection;

import com.aircraft.codelab.pioneer.pojo.dto.RealUserContractDto;
import com.aircraft.codelab.pioneer.pojo.dto.UserContractDto;
import com.aircraft.codelab.pioneer.pojo.entity.LoanContract;
import com.aircraft.codelab.pioneer.pojo.vo.UpdateTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoanContractMapper {
    LoanContract selectByPrimaryKey(Long id);

    int insertBatch(@Param("loanContractCollection") Collection<LoanContract> loanContractCollection);

    List<UserContractDto> selectUserContractList(@Param("userId") Long userId, @Param("contractState") Integer contractState);

    List<UserContractDto> selectUserContract(@Param("userId") Long userId, @Param("contractState") Integer contractState);

    RealUserContractDto selectRealUserContract(@Param("userId") Long userId, @Param("contractState") Integer contractState);

    int updateContractState(UpdateTaskVo updateTaskVo);

    int updateBatch(@Param("taskVoList") List<UpdateTaskVo> taskVoList);

    int batchUpdateCaseWhen(@Param("taskVoList") List<UpdateTaskVo> taskVoList);
}