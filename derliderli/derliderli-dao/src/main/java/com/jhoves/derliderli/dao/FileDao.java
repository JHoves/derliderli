package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.File;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author JHoves
 * @create 2023-02-07 11:01
 */
@Mapper
public interface FileDao {
    Integer addFile(File file);

    File getFileByMD5(String md5);
}

