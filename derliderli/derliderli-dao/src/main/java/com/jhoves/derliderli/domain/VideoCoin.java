package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-08 11:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCoin {
    private Long id;

    private Long videoId;

    private Long userId;

    private Integer amount;

    private Date createTime;

    private Date updateTime;

}
