package dev.jorm.spring.autoconfigure;

import dev.jorm.db.ConnectionManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@AutoConfiguration
@ConditionalOnClass(DataSource.class)
public class JormAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConnectionManager connectionManager(DataSource dataSource) {
        return new ConnectionManager(dataSource);
    }
}
