package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.User;
import com.jhoves.derliderli.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author JHoves
 * @create 2023-01-04 14:43
 */
@Mapper
public interface UserDao {

    //根据手机号获取用户
    User getUserByPhone(String phone);

    //添加用户
    Integer addUser(User user);

    //添加用户信息
    Integer addUserInfo(UserInfo userInfo);

    //根据用户id获取用户信息
    User getUserById(Long userId);

    //根据用户id获取用户具体信息
    UserInfo getUserInfoByUserId(Long userId);
}
