package com.jhoves.derliderli.domain;

import lombok.Data;

import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-05 14:50
 */
@Data
public class PageResult<T> {
    private Integer total;

    private List<T> list;

    public PageResult(Integer total,List<T> list){
        this.total = total;
        this.list = list;
    }
}
