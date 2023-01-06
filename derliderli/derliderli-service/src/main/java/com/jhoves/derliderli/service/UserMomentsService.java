package com.jhoves.derliderli.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhoves.derliderli.dao.UserMomentsDao;
import com.jhoves.derliderli.domain.UserMoment;
import com.jhoves.derliderli.domain.constant.UserMomentConstant;
import com.jhoves.derliderli.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author JHoves
 * @create 2023-01-06 10:45
 */
@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //新增用户动态
    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
        //在mq发送一条消息
        DefaultMQProducer producer = (DefaultMQProducer)applicationContext.getBean("momentsProducer");
        Message msg = new Message(UserMomentConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes(StandardCharsets.UTF_8));
        RocketMQUtil.syncSendMsg(producer,msg);
    }


    public List<UserMoment> getUserSubscribedMoment(Long userId) {
        String key = "subscribed-" + userId;
        String listStr = redisTemplate.opsForValue().get(key);
        return JSONArray.parseArray(listStr,UserMoment.class);
    }
}
