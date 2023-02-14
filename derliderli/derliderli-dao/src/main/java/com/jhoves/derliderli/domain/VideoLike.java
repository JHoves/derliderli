package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-08 9:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoLike {
    private Long id;

    private Long userId;

    private Long videoId;

    private Date createTime;
}
