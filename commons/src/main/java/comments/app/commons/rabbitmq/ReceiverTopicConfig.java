package comments.app.commons.rabbitmq;


import comments.app.commons.utils.yaml.*;
import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Data
@Configuration
@PropertySource(value = "classpath:rabbitmq.yml", factory = YamlPropertySourceFactory.class)
@PropertySource(value = "classpath:rabbitmq-${spring.profiles.active}.yml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
public class ReceiverTopicConfig {

    @Value("${rabbitmq.exchanges.receiver}")
    private String receiverExchange;

    @Value("${rabbitmq.queues.receiver}")
    private String receiverQueue;

    @Value("${rabbitmq.routing-keys.receiver}")
    private String receiverRoutingKey;


    @Bean
    public TopicExchange receiverTopicExchange() {
        return new TopicExchange(this.receiverExchange);
    }


    @Bean
    public Queue receiverQueue(){
        return new Queue(this.receiverQueue);
    }

    @Bean
    public Binding receiverBinding(){
        return BindingBuilder
                .bind(receiverQueue())
                .to(receiverTopicExchange())
                .with(this.receiverRoutingKey);
    }




}
