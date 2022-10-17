package com.aircraft.codelab.pioneer.mapper;

import com.aircraft.codelab.pioneer.pojo.entity.LoanUser;

public interface LoanUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanUser record);

    int insertSelective(LoanUser record);

    LoanUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanUser record);

    int updateByPrimaryKey(LoanUser record);
}