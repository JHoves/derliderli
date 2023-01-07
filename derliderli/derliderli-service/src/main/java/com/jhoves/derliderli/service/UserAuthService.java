package com.jhoves.derliderli.service;

import com.jhoves.derliderli.domain.auth.AuthRoleElementOperation;
import com.jhoves.derliderli.domain.auth.AuthRoleMenu;
import com.jhoves.derliderli.domain.auth.UserAuthorities;
import com.jhoves.derliderli.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author JHoves
 * @create 2023-01-07 15:13
 */
@Service
public class UserAuthService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthRoleService authRoleService;

    public UserAuthorities getUserAuthorities(Long userId) {
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdSet = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        //查一下操作权限
        List<AuthRoleElementOperation> roleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdSet);
        //查一下菜单权限
        List<AuthRoleMenu> authRoleMenuList = authRoleService.getAuthRoleMenusByRoleIds(roleIdSet);
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setRoleElementOperationList(roleElementOperationList);
        userAuthorities.setRoleMenuList(authRoleMenuList);
        return userAuthorities;
    }
}
