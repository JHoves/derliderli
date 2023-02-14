package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * @author JHoves
 * @create 2023-02-07 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    private Long id;

    //用户id
    private Long userId;

    //视频链接
    private String url;

    //封面
    private String thumbnail;

    //标题
    private String title;

    //0 自制 1 装载
    private String type;

    //时长
    private String duration;

    //分区
    private String area;

    //标签列表
    private List<VideoTag> videoTagList;

    //视频简介
    private String description;

    private Date createTime;

    private Date updateTime;
}
