package comments.app.rabbitmq;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.amqp.core.AmqpTemplate;

@Slf4j
@Component
@AllArgsConstructor
public class RabbitMQMessageProducer {


    private final AmqpTemplate ampqTemplate;

    public void publish(Object payload, String exchange, String routingKey){

        log.info("Publishing to {} using routingKey {}, payload: {}", exchange, routingKey, payload);
        ampqTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
