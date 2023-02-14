package com.jhoves.derliderli.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-14 21:54
 */
@Mapper
public interface UserCoinDao {
    Integer getUserCoinsAmount(Long userId);

    Integer updateUserCoinsAmount(@Param("userId") Long userId,
                                  @Param("amount") Integer amount,
                                  @Param("updateTime") Date updateTime);
}
