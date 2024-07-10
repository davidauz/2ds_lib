package com.davidauz.library_module.configuration;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@Slf4j
@EnableJpaRepositories
(   basePackages = "com.davidauz.library_module.repository.mssqlRepos"
,   entityManagerFactoryRef = "mssqlTableEntityManagerFactory"
,   transactionManagerRef= "mssqlTableTransactionManager"
)
@EnableAutoConfiguration
public class mssqlDataSourceConfiguration {

    @Bean
    @Primary // this is to make sure that these beans are picked up by anything which does not specify a @Qualifier
    @ConfigurationProperties("app.datasource.mssql")
    public DataSourceProperties MssqlTableDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.mssql")
    public DataSource MssqlTableDataSource() {
        return MssqlTableDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }


//    It’s mandatory to annotate at least one data source with the @Primary annotation.
//    Configure multiple data sources can’t anymore specify the JPA properties like dialect and ddl.auto
//    in the properties file, those should be included in the configuration classes separately
//    for each data source.
    @Primary
    @Bean(name = "mssqlTableEntityManagerFactory") // write the right capital letters!
    @ConfigurationProperties("app.datasource.mssql")
    public LocalContainerEntityManagerFactoryBean MssqlTableEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, String> mssqlJpaProperties = new HashMap<>();
        return builder
                .dataSource(MssqlTableDataSource())
                .properties(mssqlJpaProperties)
                .packages("com.davidauz.library_module") // this solves all the problems with
// Entity not managed and the other weird error too long to write here
                .build();
    }

    @Primary
    @Bean
    @ConfigurationProperties("app.datasource.mssql")
    public PlatformTransactionManager mssqlTableTransactionManager(
            final @Qualifier("mssqlTableEntityManagerFactory") LocalContainerEntityManagerFactoryBean MssqlTableEntityManagerFactory) {
        return new JpaTransactionManager(MssqlTableEntityManagerFactory.getObject());
    }

}
