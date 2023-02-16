package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @author JHoves
 * @create 2023-02-07 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "videos")
public class Video {
    @Id
    private Long id;

    //用户id
    @Field(type = FieldType.Long)
    private Long userId;

    //视频链接
    private String url;

    //封面
    private String thumbnail;

    //标题
    @Field(type = FieldType.Text)
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
    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;
}
