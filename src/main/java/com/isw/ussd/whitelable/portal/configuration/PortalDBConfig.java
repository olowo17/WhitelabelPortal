package com.isw.ussd.whitelable.portal.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",
        basePackages = {"com.isw.ussd.whitelable.portal.repositories.portal"},
        transactionManagerRef = "transactionManager")
public class PortalDBConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.portal.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("datasource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
//        if(activeProfile.equals("prod")) {
//            properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
//            properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//        }
        return builder.dataSource(dataSource)
                .packages("com.isw.ussd.whitelable.portal.entities.portal").properties(properties).persistenceUnit("portal").build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
