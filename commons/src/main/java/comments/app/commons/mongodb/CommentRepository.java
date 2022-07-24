package comments.app.commons.mongodb;

import org.springframework.data.mongodb.repository.*;

public interface CommentRepository extends MongoRepository<MongodbComment, String> {


}
