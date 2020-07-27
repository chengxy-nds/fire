package com.fire.repository.mapper;

import com.fire.repository.model.FireUser;
import java.util.List;

public interface FireUserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(FireUser record);

    FireUser selectByPrimaryKey(Integer userId);

    List<FireUser> selectAll();

    int updateByPrimaryKey(FireUser record);
}