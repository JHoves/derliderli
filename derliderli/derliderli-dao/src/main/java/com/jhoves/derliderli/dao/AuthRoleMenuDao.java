package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author JHoves
 * @create 2023-01-07 16:38
 */
@Mapper
public interface AuthRoleMenuDao {
    List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet);
}
