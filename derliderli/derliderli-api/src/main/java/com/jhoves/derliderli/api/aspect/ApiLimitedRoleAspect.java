package com.jhoves.derliderli.api.aspect;

import com.jhoves.derliderli.api.support.UserSupport;
import com.jhoves.derliderli.domain.annotation.ApiLimitedRole;
import com.jhoves.derliderli.domain.auth.UserRole;
import com.jhoves.derliderli.domain.exception.ConditionException;
import com.jhoves.derliderli.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author JHoves
 * @create 2023-01-07 20:35
 */
@Aspect
@Order(1)
@Component
public class ApiLimitedRoleAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    //切点
    @Pointcut("@annotation(com.jhoves.derliderli.domain.annotation.ApiLimitedRole)")
    public void check(){

    }

    //切点之后的逻辑
    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole){
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();

        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());

        //取二者的交集
        roleCodeSet.retainAll(limitedRoleCodeSet);

        if(roleCodeSet.size() > 0){
            throw new ConditionException("权限不足！");
        }
    }

}
