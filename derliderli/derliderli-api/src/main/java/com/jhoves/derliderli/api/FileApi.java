package com.jhoves.derliderli.api;

import com.jhoves.derliderli.domain.JsonResponse;
import com.jhoves.derliderli.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author JHoves
 * @create 2023-02-07 10:49
 */
public class FileApi {
    @Autowired
    private FileService fileService;

    //实现分片续传
    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile slice,
                                                   String fileMd5,
                                                   Integer sliceNo,
                                                   Integer totalSliceNo) throws Exception{
        String filePath = fileService.uploadFileBySlices(slice,fileMd5,sliceNo,totalSliceNo);
        return new JsonResponse<>(filePath);
    }

    //实现秒传
    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception {
        String fileMD5 = fileService.getFileMD5(file);
        return new JsonResponse<>(fileMD5);

    }
}

