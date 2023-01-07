package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-07 15:13
 */
@Mapper
public interface UserRoleDao {

    List<UserRole> getUserRoleByUserId(Long userId);
}
