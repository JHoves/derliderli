package com.jhoves.derliderli.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author JHoves
 * @create 2023-01-04 11:50
 */
@Mapper
public interface DemoDao {
    public Long query(long id);
}
