package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.UserRoleDao;
import com.jhoves.derliderli.domain.auth.UserRole;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-07 16:05
 */
@Service
public class UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    //根据id获取用户角色
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }

    public void addUserRole(UserRole userRole) {
        userRole.setCreateTime(new Date());
        userRoleDao.addUserRole(userRole);
    }
}
