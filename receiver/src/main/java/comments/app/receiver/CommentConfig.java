package comments.app.receiver;


import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Data
@Configuration
public class CommentConfig {

    @Value("${rabbitmq.exchanges.receiver}")
    private String internalExchange;

    @Value("${rabbitmq.queues.receiver}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.receiver}")
    private String internalNotificationRoutingKey;


    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }


    @Bean
    public Queue notificationQueue(){
        return new Queue(this.notificationQueue);
    }

    @Bean
    public Binding internalToNotificationQueue(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }




}
