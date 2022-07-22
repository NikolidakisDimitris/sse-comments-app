package comments.app.sse;

import org.springframework.data.mongodb.repository.*;

public interface CommentRepository extends MongoRepository<MongodbComment, String> {


}
