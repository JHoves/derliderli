package com.jhoves.derliderli.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-01-06 10:47
 */
@Data
public class UserMoment {
    private Long id;

    private Long userId;

    private String type;

    private Long contentId;

    private Date createTime;

    private Date updateTime;
}
