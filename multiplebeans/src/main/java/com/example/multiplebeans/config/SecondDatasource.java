package com.example.multiplebeans.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager",
        basePackages = "com.example.multiplebeans.repository.second"
)
public class SecondDatasource {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari.second") // 외부 설정을 주입 받는 객체라는 뜻이다.
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
//    @Qualifier("secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondEntityManager().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondDataSource());

        // Entity 패키지 경로
        em.setPackagesToScan("com.example.multiplebeans.entity.second");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(adapter);

        // Hibernate 설정
        Properties properties = new Properties();
//        properties.put("hibernate.physical_naming_strategy" , new CamelCaseToUnderscoresNamingStrategy());
        properties.put("hibernate.show_sql" , "true");
        properties.put("hibernate.format_sql" , "true");
        properties.put("hibernate.hbm2ddl.auto", "create");
        em.setJpaProperties(properties);
        em.setPersistenceUnitName("secondEntityManager");

        return em;
    }
}
