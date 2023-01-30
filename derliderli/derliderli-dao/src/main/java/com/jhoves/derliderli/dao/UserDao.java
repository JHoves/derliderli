package com.jhoves.derliderli.dao;

import com.alibaba.fastjson.JSONObject;
import com.jhoves.derliderli.domain.RefreshTokenDetail;
import com.jhoves.derliderli.domain.User;
import com.jhoves.derliderli.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    //更新用户信息
    Integer updateUsers(User user);

    //更新用户具体信息
    Integer updateUserInfos(UserInfo userInfo);

    //获取用户信息列表
    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);

    Integer pageCountUserInfos(Map<String,Object> params);

    List<UserInfo> pageListUserInfos(Map<String,Object> params);

    Integer deleteRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId);

    Integer addRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") Long userId, @Param("createTime") Date createTime);

    RefreshTokenDetail getRefreshTokenDetail(String refreshToken);
}
