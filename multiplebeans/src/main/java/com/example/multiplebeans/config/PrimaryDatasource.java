package com.example.multiplebeans.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManager",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = "com.example.multiplebeans.repository.primary"
)
public class PrimaryDatasource {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari.primary") // 외부 설정을 주입 받는 객체라는 뜻이다.
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
//    @Primary
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(primaryEntityManager().getObject());

        return transactionManager;
    }

    @Bean
//    @Primary
    public LocalContainerEntityManagerFactoryBean primaryEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(primaryDataSource());

        // Entity 패키지 경로
        em.setPackagesToScan("com.example.multiplebeans.entity.primary");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(adapter);

        // Hibernate 설정
        Properties properties = new Properties();
//        properties.put("hibernate.physical_naming_strategy" , new CamelCaseToUnderscoresNamingStrategy());
        properties.put("hibernate.show_sql" , "true");
        properties.put("hibernate.format_sql" , "true");
        em.setJpaProperties(properties);
        em.setPersistenceUnitName("primaryEntityManager");

        return em;
    }
}
