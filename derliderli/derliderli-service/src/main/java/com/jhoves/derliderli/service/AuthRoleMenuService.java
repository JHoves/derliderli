package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.AuthRoleMenuDao;
import com.jhoves.derliderli.domain.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author JHoves
 * @create 2023-01-07 16:17
 */
@Service
public class AuthRoleMenuService {

    @Autowired
    private AuthRoleMenuDao authRoleMenuDao;

    public List<AuthRoleMenu> getAuthRoleMenusByRoleIds(Set<Long> roleIdSet) {
        return authRoleMenuDao.getAuthRoleMenusByRoleIds(roleIdSet);
    }
}
