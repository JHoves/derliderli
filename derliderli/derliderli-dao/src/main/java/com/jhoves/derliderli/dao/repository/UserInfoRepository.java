package com.jhoves.derliderli.dao.repository;

import com.jhoves.derliderli.domain.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author JHoves
 * @create 2023-02-15 16:26
 */
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {
}
