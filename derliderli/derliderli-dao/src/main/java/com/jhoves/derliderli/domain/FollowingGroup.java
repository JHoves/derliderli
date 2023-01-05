package com.jhoves.derliderli.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-05 10:21
 * 关注分组
 */
@Data
public class FollowingGroup {
    private Long id;

    private Long userId;

    private String name;

    private String type;

    private Date createTime;

    private Date updateTime;

    private List<UserInfo> followingUserInfoList;
}
