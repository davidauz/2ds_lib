package com.davidauz.library_module.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Slf4j
@EnableJpaRepositories
(   basePackages = "com.davidauz.library_module.repository.mysqlRepos"
,   entityManagerFactoryRef = "mysqlTableEntityManagerFactory"
,   transactionManagerRef= "mysqlTableTransactionManager"
)
public class mysqlDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("app.datasource.mysql")
    public DataSourceProperties mysqlTableDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.mysql")
    public DataSource mysqlTableDataSource() {
        return mysqlTableDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "mysqlTableEntityManagerFactory")
    @ConfigurationProperties("app.datasource.mysql")
    public LocalContainerEntityManagerFactoryBean mysqlTableEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(mysqlTableDataSource())
                .packages("com.davidauz.library_module") // this solves all the problems with
// Entity not managed and the other weird error too long to write here
                .build();
    }

    @Bean
    @ConfigurationProperties("app.datasource.mysql")
    public PlatformTransactionManager mysqlTableTransactionManager(
            final @Qualifier("mysqlTableEntityManagerFactory") LocalContainerEntityManagerFactoryBean mysqlTableEntityManagerFactory) {
        return new JpaTransactionManager(mysqlTableEntityManagerFactory.getObject());
    }

}
