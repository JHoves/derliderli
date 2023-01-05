package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.FollowingGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-05 10:23
 */
@Mapper
public interface FollowingGroupDao {

    FollowingGroup getByType(String type);

    FollowingGroup getById(Long id);

    List<FollowingGroup> getByUserId(Long userId);

    Integer addFollowingGroup(FollowingGroup followingGroup);

    List<FollowingGroup> getUserFollowingGroups(Long userId);
}

