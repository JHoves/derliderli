package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author JHoves
 * @create 2023-01-06 10:46
 */
@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoment userMoment);
}
