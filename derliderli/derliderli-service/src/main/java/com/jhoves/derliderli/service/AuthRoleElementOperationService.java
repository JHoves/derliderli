package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.AuthRoleElementOperationDao;
import com.jhoves.derliderli.domain.auth.AuthRoleElementOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author JHoves
 * @create 2023-01-07 16:17
 */
@Service
public class AuthRoleElementOperationService {
    @Autowired
    private AuthRoleElementOperationDao authRoleElementOperationDao;

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdSet) {
        return authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIdSet);
    }
}
