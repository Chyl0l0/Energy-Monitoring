package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Producer implements Runnable {
    UUID deviceId;
    Timestamp timestamp;
    Float value;
    Scanner scanner;
    ConnectionFactory connectionFactory;
    public Producer(UUID deviceId)  {

        connectionFactory =new ConnectionFactory();
        connectionFactory.setHost("localhost");
        this.deviceId = deviceId;
        try {
            scanner = new Scanner(new File("sensor.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    void sendMessage()
    {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("data", false, false, false, null);
            channel.basicPublish("", "data", null, this.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"timestamp\":" + timestamp.getTime() +
                ",\"device_id\":\"" + deviceId +
                "\",\"measurement_value\":" + value +
                '}';
    }

    @Override
    public void run() {
        while (scanner.hasNext()){

            this.timestamp = new Timestamp(System.currentTimeMillis());
            this.value=Float.valueOf(scanner.next());
            sendMessage();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }

    }
}
