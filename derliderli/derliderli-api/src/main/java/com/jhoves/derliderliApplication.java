package com.jhoves;

import com.jhoves.derliderli.service.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author JHoves
 * @create 2023-01-04 11:04
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class derliderliApplication {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(derliderliApplication.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
