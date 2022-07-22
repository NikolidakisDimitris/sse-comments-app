package comments.app.components.rabbitmq;


import comments.app.components.utils.yaml.*;
import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Data
@Configuration
@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:rabbitmq-properties.yml", factory = YamlPropertySourceFactory.class)
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
