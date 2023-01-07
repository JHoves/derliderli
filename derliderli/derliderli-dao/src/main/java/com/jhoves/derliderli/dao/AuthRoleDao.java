package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author JHoves
 * @create 2023-01-07 21:47
 */
@Mapper
public interface AuthRoleDao {
    AuthRole getRoleByCode(String code);
}
