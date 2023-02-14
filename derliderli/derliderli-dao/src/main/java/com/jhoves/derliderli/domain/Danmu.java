package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-15 0:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Danmu {
    private Long id;

    private Long userId;

    private Long videoId;

    private String content;

    private String danmuTime;

    private Date createTime;
}
