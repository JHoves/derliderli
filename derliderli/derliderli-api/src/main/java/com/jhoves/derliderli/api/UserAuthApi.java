package com.jhoves.derliderli.api;

import com.jhoves.derliderli.api.support.UserSupport;
import com.jhoves.derliderli.domain.JsonResponse;
import com.jhoves.derliderli.domain.auth.UserAuthorities;
import com.jhoves.derliderli.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JHoves
 * @create 2023-01-07 15:12
 */
@RestController
public class UserAuthApi {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserAuthService userAuthService;

    //获取用户权限
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
        UserAuthorities userAuthorities = userAuthService.getUserAuthorities(userId);
        return new JsonResponse<>(userAuthorities);
    }

}
