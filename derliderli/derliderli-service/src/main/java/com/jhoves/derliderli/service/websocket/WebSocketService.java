package com.jhoves.derliderli.service.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 需要考虑并发问题
 */
@Component
@ServerEndpoint("/imserver")
public class WebSocketService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //AtomicInteger是原子性操作的实体类
    //ONLINE_COUNT当前长连接的客户端数目
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    private static final ConcurrentHashMap<String,WebSocketService>  WEBSOCKET_MAP = new ConcurrentHashMap<>();

    private Session session;

    private String sessionId;

    //解决多例模式下bean注入的问题 ↓
    private static ApplicationContext APPLICATION_CONTEXT;

    public static void setApplicationContext(ApplicationContext applicationContext){
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    //客户端与服务端建立连接
    @OnOpen
    public void openConnection(Session session){
        this.sessionId = session.getId();
        this.session = session;
        if(WEBSOCKET_MAP.containsKey(sessionId)){
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId,this);
        }else {
            WEBSOCKET_MAP.put(sessionId,this);
            ONLINE_COUNT.getAndIncrement();
        }
        logger.info("用户连接成功：" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());
        try {
            this.sendMessage("0");
        }catch (Exception e){
            logger.error("连接异常");
        }
    }

    //关闭连接
    @OnClose
    public void closeConnection(){
        if(WEBSOCKET_MAP.containsKey(sessionId)){
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        logger.info("用户退出：" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());
    }

    @OnMessage
    public void OnMessage(String message){

    }

    @OnError
    public void OnError(Throwable error){

    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}