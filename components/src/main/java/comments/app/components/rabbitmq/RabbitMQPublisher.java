package comments.app.components.rabbitmq;

import comments.app.components.comment.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.amqp.core.AmqpTemplate;

@Slf4j
@Component
@AllArgsConstructor
public class RabbitMQPublisher {


    private final AmqpTemplate ampqTemplate;

    public void publish(Comment payload, String exchange, String routingKey){

        log.info("Publishing to {} using routingKey {}, payload: {}", exchange, routingKey, payload);
        ampqTemplate.convertAndSend(exchange, routingKey, payload);
    }

    public void publishFan(Comment payload, String exchange){

        log.info("Publishing to {} , payload: {}", exchange, payload);
        ampqTemplate.convertAndSend(exchange, "", payload);
    }
}
