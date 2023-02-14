package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.UserFollowingDao;
import com.jhoves.derliderli.domain.FollowingGroup;
import com.jhoves.derliderli.domain.User;
import com.jhoves.derliderli.domain.UserFollowing;
import com.jhoves.derliderli.domain.UserInfo;
import com.jhoves.derliderli.domain.constant.UserConstant;
import com.jhoves.derliderli.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author JHoves
 * @create 2023-01-05 10:24
 */
@Service
public class UserFollowingService {
    @Autowired
    private UserFollowingDao userFollowingDao;
    @Autowired
    private FollowingGroupService followingGroupService;
    @Autowired
    private UserService userService;

    @Transactional
    public void addUserFollowing(UserFollowing userFollowing){
        Long groupId = userFollowing.getGroupId();
        if(groupId == null){
            //获取默认分组
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if(followingGroup == null){
                throw new ConditionException("关注分组不存在！");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if(user == null){
            throw new ConditionException("关注的用户不存在！");
        }
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(),followingId);
        userFollowing.setCreateTime(new Date());
        userFollowingDao.addUserFollowing(userFollowing);
    }

    //获取用户关注列表(todo 学习逻辑)
    //第一步：获取关注的用户列表
    //第二步：根据关注用户的id查询关注用户的基本信息
    //第三步：将关注用户按关注分组进行分类
    public List<FollowingGroup> getUserFollowings(Long userId){
        List<UserFollowing> list = userFollowingDao.getUserFollowings(userId);
        Set<Long> followingIdSet = list.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfosList = new ArrayList<>();
        if(followingIdSet.size() > 0){
            userInfosList = userService.getUserInfoByUserIds(followingIdSet);
        }
        for (UserFollowing userFollowing : list){
            for (UserInfo userInfo : userInfosList){
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        //全部分组用户
        FollowingGroup allGroup = new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfosList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for (FollowingGroup group : groupList){
            List<UserInfo> infoList = new ArrayList<>();
            for (UserFollowing userFollowing : list){
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }

    //获取粉丝列表
    //第一步：获取当前用户的粉丝列表
    //第二步：根据粉丝的用户id查询基本信息
    //第三步：查询当前用户是否已经关注该粉丝
    public List<UserFollowing> getUserFans(Long userId){
        //获取用户粉丝
        List<UserFollowing> fanList = userFollowingDao.getUserFans(userId);
        Set<Long> fanIdSet = fanList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfosList = new ArrayList<>();
        if(fanIdSet.size() > 0){
            userInfosList = userService.getUserInfoByUserIds(fanIdSet);
        }
        //查询当前用户是否已经关注该粉丝
        List<UserFollowing> followingList = userFollowingDao.getUserFollowings(userId);
        for (UserFollowing fan : fanList){
            for (UserInfo userInfo : userInfosList){
                if(fan.getUserId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for (UserFollowing following : followingList){
                if(following.getFollowingId().equals(fan.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    public Long addUserFollowingGroup(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }

    public List<UserInfo> checkFollowingStatus(List<UserInfo> userInfoList, Long userId) {
        List<UserFollowing> userFollowingList = userFollowingDao.getUserFollowings(userId);
        for (UserInfo userInfo : userInfoList){
           userInfo.setFollowed(false);
           for (UserFollowing userFollowing : userFollowingList){
               if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                   userInfo.setFollowed(true);
               }
           }
        }
        return userInfoList;
    }
}
