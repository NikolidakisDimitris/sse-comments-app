package comments.app.components.rabbitmq;


import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Data
@Configuration
@PropertySource("classpath:rabbitmq-properties.yml")
public class ViewFanConfig {

    @Value("${rabbitmq.exchanges.view}")
    private String viewExchange;

    @Value("${rabbitmq.queues.view}")
    private String viewQueue;


    @Bean
    public FanoutExchange fanoutExchange() {

        return new FanoutExchange(this.viewExchange);
    }

    @Bean
    public Queue viewQueue() {

        return new Queue(this.viewQueue);
    }

    @Bean
    public Binding viewBinding() {

        return BindingBuilder.bind(viewQueue()).to(fanoutExchange());
    }
}