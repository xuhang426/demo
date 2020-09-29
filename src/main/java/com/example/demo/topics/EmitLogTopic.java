package com.example.demo.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * <h3>概要:</h3><p>EmitLogTopic</p>
 * <h3>功能:</h3><p>发送端/生产者</p>
 * <h3>履历:</h3>
 * <li>2020年04月26日  v0.1 版本内容: 新建</li>
 * @author 徐航
 * @since 0.1
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String[] LOG_LEVEL_ARR = {"dao.debug", "dao.info", "dao.error",
            "service.debug", "service.info", "service.error",
            "controller.debug", "controller.info", "controller.error"};
    
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 的主机名
        factory.setHost("localhost");
        // 创建一个连接 
        Connection connection = factory.newConnection();
        // 创建一个通道 
        Channel channel = connection.createChannel();    
        // 指定一个交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 发送消息  
        for (String severity : LOG_LEVEL_ARR) {
            String message = "Liang-MSG log : [" +severity+ "]" + UUID.randomUUID().toString();  
            // 发布消息至交换器 
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());  
            System.out.println(" [x] Sent '" + message + "'");  
        }  
        // 关闭频道和连接  
        channel.close();
        connection.close();
    }
}
