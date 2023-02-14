package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-08 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoCollection {
    private Long id;

    private Long videoId;

    private Long userId;

    private Long groupId;

    private Date createTime;
}
