package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.FollowingGroupDao;
import com.jhoves.derliderli.domain.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-05 10:24
 */
@Service
public class FollowingGroupService {
    @Autowired
    private FollowingGroupDao followingGroupDao;


    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }

    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }

    public  List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }

    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.addFollowingGroup(followingGroup);
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupDao.getUserFollowingGroups(userId);
    }
}
