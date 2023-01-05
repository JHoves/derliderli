package com.jhoves.derliderli.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-01-05 10:19
 * 用户关注
 */
@Data
public class UserFollowing {
    private Long id;

    private Long userId;

    private Long followingId;

    private Long groupId;

    private Date createTime;

    private UserInfo userInfo;
}
