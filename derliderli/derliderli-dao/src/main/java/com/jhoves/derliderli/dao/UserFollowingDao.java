package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.UserFollowing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-05 10:22
 */
@Mapper

public interface UserFollowingDao {

    Integer deleteUserFollowing(@Param("userId") Long userId, @Param("followingId") Long followingId);

    Integer addUserFollowing(UserFollowing userFollowing);

    List<UserFollowing> getUserFollowings(Long userId);

    List<UserFollowing> getUserFans(Long userId);
}
