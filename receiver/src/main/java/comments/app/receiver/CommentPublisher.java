package comments.app.receiver;

import comments.app.components.comment.*;
import comments.app.components.rabbitmq.RabbitMQPublisher;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Service
public class CommentPublisher {

    @Value("${rabbitmq.exchanges.receiver}")
    private String receiverExchange;
    @Value("${rabbitmq.routing-keys.receiver}")
    private String receiverRoutingKey;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Async
    public void publishInsert(Comment comment){
        this.rabbitMQPublisher.publish(comment, receiverExchange, receiverRoutingKey);
    }


}
