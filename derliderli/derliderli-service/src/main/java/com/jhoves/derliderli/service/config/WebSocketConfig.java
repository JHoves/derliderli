package com.jhoves.derliderli.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author JHoves
 * @create 2023-02-08 15:31
 */
@Configuration
public class WebSocketConfig {
    //用来发现websocket服务的
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
