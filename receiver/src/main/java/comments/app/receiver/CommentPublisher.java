package comments.app.receiver;

import comments.app.components.rabbitmq.RabbitMQPublisher;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;

public class CommentPublisher {

    @Value("${rabbitmq.exchanges.internal}")
    private String receiverExchange;
    @Value("${rabbitmq.routing-keys.internal-comment}")
    private String receiverRoutingKey;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Async
    public void publishInsert(CommentRequest commentRequest){
        this.rabbitMQPublisher.publish(commentRequest, receiverExchange, receiverRoutingKey);
    }


}
