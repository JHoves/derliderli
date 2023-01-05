package com.jhoves.derliderli.api;

import com.alibaba.fastjson.JSONObject;
import com.jhoves.derliderli.api.support.UserSupport;
import com.jhoves.derliderli.domain.*;
import com.jhoves.derliderli.service.UserFollowingService;
import com.jhoves.derliderli.service.UserService;
import com.jhoves.derliderli.service.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.US_ASCII;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-04 14:42
 */
@RestController
public class UserApi {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserFollowingService userFollowingService;

    //获取用户信息
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo(){
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        return new JsonResponse<>(user);
    }

    //获取RSA的公钥
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey(){
        String pk = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(pk);
    }

    //用户注册
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody User user){
        userService.addUser(user);
        return JsonResponse.success();
    }

    //用户登录
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody User user) throws Exception{
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }


    //更新用户信息
    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user) throws Exception{
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUsers(user);
        return JsonResponse.success();
    }

    //更新用户基本信息
    @PutMapping("/users-infos")
    public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo){
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfos(userInfo);
        return JsonResponse.success();
    }

    //用户分页查询
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no,@RequestParam Integer size,String nick){
        Long userId = userSupport.getCurrentUserId();
        JSONObject params = new JSONObject();
        params.put("no",no);
        params.put("size",size);
        params.put("nick",nick);
        params.put("userId",userId);
        PageResult<UserInfo> result = userService.pageListUserInfos(params);
        //判断用户关注是否被当前用户关注
        if(result.getTotal() > 0){
            List<UserInfo> checkedUserInfoList = userFollowingService.checkFollowingStatus(result.getList(), userId);
            result.setList(checkedUserInfoList);
        }
        return new JsonResponse<>(result);
    }
}
