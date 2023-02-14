package com.jhoves.derliderli.dao;

import com.jhoves.derliderli.domain.Danmu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author JHoves
 * @create 2023-02-15 0:51
 */
@Mapper
public interface DanmuDao {
    Integer addDanmu(Danmu danmu);

    List<Danmu> getDanmus(Map<String,Object> params);
}
