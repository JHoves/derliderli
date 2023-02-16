package com.jhoves.derliderli.dao.repository;

import com.jhoves.derliderli.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author JHoves
 * @create 2023-02-15 15:53
 */
public interface VideoRepository extends ElasticsearchRepository<Video,Long> {
    Video findByTitleLike(String keyword);
}
