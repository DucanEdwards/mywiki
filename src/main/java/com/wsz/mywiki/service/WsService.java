package com.wsz.mywiki.service;

import com.wsz.mywiki.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WsService {
    @Resource
    public WebSocketServer webSocketServer;

    @Async
    public void sendInfo(String message,String log_id) {
        MDC.put("LOG_ID",log_id);
        webSocketServer.sendInfo(message);
    }

}
