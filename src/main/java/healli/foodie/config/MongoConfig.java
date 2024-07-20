package healli.foodie.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import static java.util.Collections.singletonList;

@Configuration
@PropertySource("classpath:/application.properties")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment env;


    @Override
    public String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {

        var userName = env.getProperty("spring.data.mongodb.username");
        var password = env.getProperty("spring.data.mongodb.password").toCharArray();
        var host = env.getProperty("spring.data.mongodb.host");
        int port = env.getProperty("spring.data.mongodb.port", Integer.class);
        var authenticationDatabase = env.getProperty("spring.data.mongodb.authentication-database");

        builder
                .credential(MongoCredential.createCredential(userName, authenticationDatabase, password))
                .applyToClusterSettings(settings -> {
                    settings.hosts(singletonList(new ServerAddress(host, port)));
                });
    }

}
