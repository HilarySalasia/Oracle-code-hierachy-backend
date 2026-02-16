package com.turnquest.code_hierachy.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OracleDataSourceConfig {

    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "oracle.datasource")
    public DataSource oracleDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        return dataSource;
    }

    @Bean(name = "oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(1000); // Optimize for large result sets
        return jdbcTemplate;
    }

    @Bean(name = "postgresDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource postgresDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("postgresDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.turnquest.code_hierachy.models");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);
        
        return em;
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}