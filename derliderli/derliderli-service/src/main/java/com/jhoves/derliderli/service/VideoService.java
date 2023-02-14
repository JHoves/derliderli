package com.jhoves.derliderli.service;

import com.jhoves.derliderli.dao.VideoDao;
import com.jhoves.derliderli.domain.*;
import com.jhoves.derliderli.domain.exception.ConditionException;
import com.jhoves.derliderli.service.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author JHoves
 * @create 2023-02-07 20:33
 */
@Service
public class VideoService {
    @Autowired
    private VideoDao videoDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private UserCoinService userCoinService;

    @Autowired
    private UserService userService;

    @Transactional
    public void addVideos(Video video) {
        Date now = new Date();
        video.setCreateTime(now);
        videoDao.addVideos(video);

        Long videoId = video.getId();
        List<VideoTag> tagList = video.getVideoTagList();
        tagList.forEach(item -> {
            item.setCreateTime(now);
            item.setVideoId(videoId);
        });
        videoDao.batchAddVideoTags(tagList);
    }

    public PageResult<Video> pageListVideos(Integer size, Integer no, String area) {
        if(size == null || no == null){
            throw new ConditionException("参数异常！");
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("start",(no-1)*size);
        params.put("limit",size);
        params.put("area",area);

        //存放结果
        List<Video> list = new ArrayList<>();
        //符合条件的数据有多少条
        Integer total = videoDao.pageCountVideos(params);
        if(total > 0){
            //真正查数据
            list = videoDao.pageListVideos(params);
        }
        return new PageResult<>(total,list);
    }

    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url) throws Exception {
        fastDFSUtil.viewVideoOnlineBySlices(request,response,url);
    }

    public void addVideoLike(Long videoId, Long userId) {
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        VideoLike videoLike = videoDao.getVideoLikeByVideoAndUserId(videoId,userId);
        if(videoLike != null){
            throw new ConditionException("已经点赞过！");
        }
        videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLike.setCreateTime(new Date());
        videoDao.addVideoLike(videoLike);
    }

    public void deleteVideoLike(Long videoId, Long userId) {
        videoDao.deleteVideoLike(videoId,userId);
    }

    public Map<String, Object> getVideoLikes(Long videoId, Long userId) {
        Long count = videoDao.getVideoLikes(videoId);
        VideoLike videoLike = videoDao.getVideoLikeByVideoAndUserId(videoId, userId);
        boolean like = videoLike != null;
        HashMap<String, Object> result = new HashMap<>();
        result.put("count",count);
        result.put("like",like);
        return result;
    }

    public void addVideoCollection(VideoCollection videoCollection, Long userId) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if(videoId == null || groupId == null){
            throw new ConditionException("参数异常！");
        }
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        //删除原有视频收藏
        videoDao.deleteVideoCollection(videoId,userId);
        //添加新的视频收藏
        videoCollection.setUserId(userId);
        videoCollection.setCreateTime(new Date());
        videoDao.addVideoCollection(videoCollection);
    }

    public void deleteVideoCollection(Long videoId, Long userId) {
        videoDao.deleteVideoCollection(videoId,userId);
    }

    public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
        Long count = videoDao.getVideoCollections(videoId);
        VideoCollection videoCollection = videoDao.getVideoCollectionByVideoAndUserId(videoId,userId);
        boolean collection = videoCollection != null;
        HashMap<String, Object> result = new HashMap<>();
        result.put("count",count);
        result.put("collection",collection);
        return result;
    }

    //视频投币业务
    @Transactional
    public void addVideoCoins(VideoCoin videoCoin, Long userId) {
        Long videoId = videoCoin.getVideoId();
        Integer amount = videoCoin.getAmount();
        //验证视频id和视频是否合理
        if(videoId == null){
            throw new ConditionException("参数异常！");
        }
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        //查询当前登录用户是否拥有足够的硬币
        Integer userCoinsAmount = userCoinService.getUserCoinsAmount(userId);
        userCoinsAmount = userCoinsAmount == null ? 0 : userCoinsAmount;
        if(amount > userCoinsAmount){
            throw new ConditionException("硬币不足！");
        }
        //查询当前登录用户对该视频已经投了多少硬币
        VideoCoin dbVideoCoin = videoDao.getVideoCoinByVideoIdAndUserId(videoId,userId);
        //新增视频投币
        if (dbVideoCoin == null){
            videoCoin.setUserId(userId);
            videoCoin.setCreateTime(new Date());
            videoDao.addVideoCoin(videoCoin);
        }else {
            Integer dbAmount = dbVideoCoin.getAmount();
            dbAmount += amount;
            //更新视频投币
            videoCoin.setUserId(userId);
            videoCoin.setAmount(dbAmount);
            videoCoin.setUpdateTime(new Date());
            videoDao.updateVideoCoin(videoCoin);
        }
        //更新用户当前硬币总数
        userCoinService.updateUserCoinsAmount(userId,(userCoinsAmount-amount));
    }

    //查询视频投币数量业务
    public Map<String, Object> getVideoCoins(Long videoId, Long userId) {
        Long count = videoDao.getVideoCoinsAmount(videoId);
        VideoCoin videoCollection = videoDao.getVideoCoinByVideoIdAndUserId(videoId, userId);
        boolean like = videoCollection != null;
        Map<String,Object> result = new HashMap<>();
        result.put("count",count);
        result.put("like",like);
        return result;
    }

    //添加视频评论业务
    public void addVideoComment(VideoComment videoComment, Long userId) {
        Long videoId = videoComment.getVideoId();
        if(videoId == null){
            throw new ConditionException("参数异常！");
        }
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        videoComment.setUserId(userId);
        videoComment.setCreateTime(new Date());
        videoDao.addVideoComment(videoComment);
    }

    //分页查询视频评论业务
    public PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId) {
        Video video = videoDao.getVideoById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("start",(no-1)*size);
        params.put("limit",size);
        params.put("videoId",videoId);
        Integer total = videoDao.pageCountVideoComments(params);
        List<VideoComment> list = new ArrayList<>();
        if(total > 0){
            list = videoDao.pageListVideoComments(params);
            //批量查询二级评论
            List<Long> parentIdList = list.stream().map(VideoComment::getId).collect(Collectors.toList());
            List<VideoComment> childCommentList = videoDao.batchGetVideoCommentsByRootIds(parentIdList);
            //批量查询用户信息
            Set<Long> userIdList = list.stream().map(VideoComment::getUserId).collect(Collectors.toSet());
            Set<Long> replyUserIdList = childCommentList.stream().map(VideoComment::getUserId).collect(Collectors.toSet());
            Set<Long> childUserIdList = childCommentList.stream().map(VideoComment::getReplyUserId).collect(Collectors.toSet());
            userIdList.addAll(replyUserIdList);
            userIdList.addAll(childUserIdList);
            List<UserInfo> userInfoList = userService.batchGetUserInfoByUserIds(userIdList);
            Map<Long,UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(UserInfo::getUserId,userInfo -> userInfo));
            list.forEach(comment -> {
                Long id = comment.getId();
                List<VideoComment> childList = new ArrayList<>();
                childCommentList.forEach(child -> {
                    if(id.equals(child.getRootId())){
                        child.setUserInfo(userInfoMap.get(child.getUserId()));
                        child.setReplyUserInfo(userInfoMap.get(child.getReplyUserId()));
                        childList.add(child);
                    }
                });
                comment.setChildList(childList);
                comment.setUserInfo(userInfoMap.get(comment.getUserId()));
            });
        }
        return new PageResult<>(total,list);
    }

    //获取视频详情业务
    public Map<String, Object> getVideoDetails(Long videoId) {
        Video video = videoDao.getVideoDetails(videoId);
        Long userId = video.getUserId();
        User userInfo = userService.getUserInfo(userId);
        Map<String,Object> result = new HashMap<>();
        result.put("video",video);
        result.put("userInfo",userInfo);
        return result;
    }
}
