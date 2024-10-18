package com.wora.config;

import com.wora.common.util.PropertiesReader;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@EnableJpaRepositories(basePackages = "com.wora")
@ComponentScan("com.wora")
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(PropertiesReader.get("DB_URL"));
        ds.setUsername(PropertiesReader.get("DB_USERNAME"));
        ds.setPassword(PropertiesReader.get("DB_PASSWORD"));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.wora");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", PropertiesReader.get("HIBERNATE_DDL_AUTO"));
        factoryBean.setJpaProperties(jpaProperties);
        jpaProperties.put("hibernate.show_sql", PropertiesReader.get("HIBERNATE_SHOW_SQL"));
        jpaProperties.put("hibernate.format_sql", PropertiesReader.get("HIBERNATE_FORMAT_SQL"));

        /*
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", PropertiesReader.get("HIBERNATE_DIALECT"));
        jpaProperties.put("hibernate.hbm2ddl.auto", PropertiesReader.get("HIBERNATE_DDL_AUTO"));
        factoryBean.setJpaProperties(jpaProperties);
        factoryBean.setJpaProperties(jpa);
         */
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager tx = new JpaTransactionManager();
        tx.setEntityManagerFactory(entityManagerFactory);
        return tx;
    }
}
