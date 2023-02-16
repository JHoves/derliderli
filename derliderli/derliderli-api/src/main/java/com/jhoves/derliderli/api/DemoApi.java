package com.jhoves.derliderli.api;

import com.jhoves.derliderli.domain.JsonResponse;
import com.jhoves.derliderli.domain.Video;
import com.jhoves.derliderli.service.ElasticsearchService;
import com.jhoves.derliderli.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author JHoves
 * @create 2023-02-07 10:57
 */
public class DemoApi {
    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private ElasticsearchService elasticsearchService;

    //测试分片效果
    @GetMapping("/slices")
    public void slices(MultipartFile file) throws Exception{
        fastDFSUtil.convertFileToSlices(file);
    }

    //测试查询es中的视频
    @GetMapping("/es-videos")
    public JsonResponse<Video> getEsVideos(@RequestParam String keyword){
        Video video = elasticsearchService.getVideos(keyword);
        return new JsonResponse<>(video);
    }
}
