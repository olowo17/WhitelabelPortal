package com.isw.ussd.whitelable.portal.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
        basePackages = {"com.isw.ussd.whitelable.portal.repositories.user"},
        transactionManagerRef = "userTransactionManager")
public class UserDBConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean(name = "userDatasource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("userDatasource") DataSource dataSource) {
        Map<String, String> props = new HashMap<>();
//        if (activeProfile.equals("prod")) {
//            props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
//            props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//        }
        //props.put("hibernate.hbm2ddl.auto", "update");
        return builder.dataSource(dataSource).properties(props)
                .packages("com.isw.ussd.whitelable.portal.entities.user").persistenceUnit("user").build();
    }

    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);

    }
}
