package com.aircraft.codelab.labcore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2021-12-01
 * tomcat-websocket
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Controller
@ServerEndpoint("/socket/{uid}")
public class WebSocketServer {
    // 记录当前在线人数
    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    public static final Map<String, Session> connections = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("uid") String key, Session session) {
        connections.put(key, session);
        onlineCount.incrementAndGet();
        log.info("新用户: {}加入,当前在线人数: {}", key, onlineCount.get());
    }

    @OnMessage
    public void onMessage(@PathParam("uid") String key, byte[] data) throws IOException {
        log.debug("收到用户: {}的消息,data: {}", key, data.length);
        //发送数据
        Session session = connections.get(key);
        session.getBasicRemote().sendText("word");
    }

//    @OnMessage
//    public void onMessage(@PathParam("uid") String key, String data) throws IOException {
//        log.debug("收到用户: {}的消息: {}", key, data);
//        byte[] tts = new byte[0];
//        ByteBuffer byteBuffer = ByteBuffer.wrap(tts);
//        connections.get(key).getBasicRemote().sendBinary(byteBuffer);
//    }

    @OnClose
    public void onClose(@PathParam("uid") String key) {
        connections.remove(key);
        onlineCount.decrementAndGet();
        log.info("{}连接关闭,当前在线人数为：{}", key, onlineCount.get());
    }

    @OnError
    public void onError(@PathParam("uid") String key, Throwable error) {
        log.error("用户: {}连接发生错误", key, error);
    }
}
