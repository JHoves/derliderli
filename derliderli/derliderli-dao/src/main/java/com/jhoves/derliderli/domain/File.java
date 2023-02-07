package com.jhoves.derliderli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-07 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private Long id;

    private String url;

    private String type;

    private String md5;

    private Date createTime;
}
