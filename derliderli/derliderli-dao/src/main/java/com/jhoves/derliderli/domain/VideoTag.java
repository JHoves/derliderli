package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-07 19:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoTag {
    private Long id;

    private Long videoId;

    private Long tagId;

    private Date createTime;
}
