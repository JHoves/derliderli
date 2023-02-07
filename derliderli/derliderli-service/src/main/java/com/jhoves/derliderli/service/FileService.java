package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.FileDao;
import com.jhoves.derliderli.domain.File;
import com.jhoves.derliderli.service.util.FastDFSUtil;
import com.jhoves.derliderli.service.util.MD5Util;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author JHoves
 * @create 2023-02-07 10:50
 */
@Service
public class FileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    public String uploadFileBySlices(MultipartFile slice,
                                     String fileMd5,
                                     Integer sliceNo,
                                     Integer totalSliceNo) throws Exception{
        File dbFileMD5 = fileDao.getFileByMD5(fileMd5);
        if(dbFileMD5 != null){
            return dbFileMD5.getUrl();
        }
        String url = fastDFSUtil.uploadFileBySlices(slice, fileMd5, sliceNo, totalSliceNo);
        if(!StringUtil.isNullOrEmpty(url)){
            dbFileMD5 = new File();
            dbFileMD5.setCreateTime(new Date());
            dbFileMD5.setMd5(fileMd5);
            dbFileMD5.setUrl(url);
            dbFileMD5.setType(fastDFSUtil.getFileType(slice));
            fileDao.addFile(dbFileMD5);
        }
        return url;
    }

    public String getFileMD5(MultipartFile file) throws Exception{
        return MD5Util.getFileMD5(file);
    }

    public File getFileByMd5(String fileMd5){
        return fileDao.getFileByMD5(fileMd5);
    }
}
