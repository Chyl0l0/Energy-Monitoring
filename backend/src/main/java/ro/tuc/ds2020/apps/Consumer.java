package ro.tuc.ds2020.apps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.EnergyConsumptionRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Component
public class Consumer {
    ConnectionFactory connectionFactory;
    private  EnergyConsumptionRepository energyConsumptionRepository;
    private DeviceRepository deviceRepository;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    String password;
    private NotificationController notification;
    @Autowired
    public Consumer( @Value("${spring.rabbitmq.host}") String host, EnergyConsumptionRepository energyConsumptionRepository, DeviceRepository deviceRepository, NotificationController notification) {
        this.notification = notification;
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        //connectionFactory.setUsername(username);
        //connectionFactory.setPassword(password);
        System.out.println(host);
        this.energyConsumptionRepository = energyConsumptionRepository;
        this.deviceRepository = deviceRepository;
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("data", false, false, false, null);
            //NotificationController notificationController = new NotificationController();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println( message  );

                JSONObject jsonObject = new JSONObject(message);
                Timestamp timestamp =new Timestamp(jsonObject.getLong("timestamp"));
                float value = jsonObject.getFloat("measurement_value");
                UUID uuid = UUID.fromString(jsonObject.getString("device_id"));
                Optional<Device> device= deviceRepository.findById(uuid);

                if (device.isPresent()) {
                    try {
                        if (value > device.get().getMax_consumption()) {
                            notification.sendMessage(message);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    EnergyConsumption energyConsumption = new EnergyConsumption(timestamp, value,device.get());
                    energyConsumptionRepository.save(energyConsumption);
                }
            };
            channel.basicConsume("data", true, deliverCallback, consumerTag -> { });

        } catch (IOException e) {
            System.out.println(host);
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println(host);

            throw new RuntimeException(e);
        }

    }
}
