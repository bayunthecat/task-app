package com.mine.app.configuration;

import com.mine.app.model.repository.redis.TaskDb;
import com.mine.app.model.repository.redis.TaskPk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.user}")
    private String user;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        final var conf = new RedisStandaloneConfiguration();
        conf.setHostName(host);
        conf.setPort(port);
        conf.setPassword(password);
        conf.setUsername(user);
        return new JedisConnectionFactory(conf);
    }

    @Bean
    public RedisTemplate<TaskPk, TaskDb> redisTemplate(RedisConnectionFactory connectionFactory) {
        final var template = new RedisTemplate<TaskPk, TaskDb>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
