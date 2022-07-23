package comments.app.commons.mongodb;

import comments.app.commons.mongodb.*;
import comments.app.commons.utils.yaml.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.repository.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.*;

//@Component
//@ConfigurationProperties(prefix = "yaml")
//@PropertySource(value = "classpath:rabbitmq-properties.yml", factory = YamlPropertySourceFactory.class)
public interface CommentRepository extends MongoRepository<MongodbComment, String> {


}
