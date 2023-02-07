package com.jhoves.derliderli.api;

import com.jhoves.derliderli.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author JHoves
 * @create 2023-02-07 10:57
 */
public class DemoApi {
    @Autowired
    private FastDFSUtil fastDFSUtil;

    //测试分片效果
    @GetMapping("/slices")
    public void slices(MultipartFile file) throws Exception{
        fastDFSUtil.convertFileToSlices(file);
    }
}
