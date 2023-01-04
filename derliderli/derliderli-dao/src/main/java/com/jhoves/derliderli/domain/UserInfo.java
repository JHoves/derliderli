package com.jhoves.derliderli.domain;

import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.Data;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-01-04 14:38
 */
@Data
public class UserInfo {
    private Long id;

    private Long userId;

    private String nick;

    private String avatar;

    private String sign;

    private String gender;

    private String birth;

    private Date createTime;

    private Date updateTime;

}
