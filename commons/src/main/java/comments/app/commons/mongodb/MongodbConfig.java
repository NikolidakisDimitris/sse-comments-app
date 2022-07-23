package comments.app.commons.mongodb;

import com.mongodb.*;
import com.mongodb.client.*;
import comments.app.commons.utils.yaml.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.*;

@Configuration
@PropertySource(value = "classpath:mongodb-properties.yml", factory = YamlPropertySourceFactory.class)
public class MongodbConfig extends AbstractMongoClientConfiguration {

    //mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]

    @Value("${data.mongodb.port}")
    private String port;
    @Value("${data.mongodb.username}")
    private String username;
    @Value("${data.mongodb.password}")
    private String password;
    @Value("${data.mongodb.host}")
    private String host;
    @Value("${data.mongodb.database}")
    private String database;
    @Value("${data.mongodb.authentication-source}")
    private String authSource;
    @Value("#{new Boolean('${data.mongodb.auto-index-creation}')}")
    private Boolean autoIndexCreation;


    @Override
    public MongoClient mongoClient() {

        String sb = "mongodb://" +
                username +
                ":" +
                password +
                "@" +
                host +
                ":" +
                port +
                "/?authSource=" +
                authSource;

        ConnectionString connectionString = new ConnectionString(sb);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {

        System.out.println("DATABASE NAME:" + this.database );

        return this.database;
    }

    @Override
    protected boolean autoIndexCreation() {

        return this.autoIndexCreation;
    }
}