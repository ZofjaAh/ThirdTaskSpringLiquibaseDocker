package com.aston.thirdTask.infrastructure.configuration;

import com.aston.thirdTask.infrastructure.database.entity._EntityMarker;
import com.aston.thirdTask.infrastructure.database.repository.jpa._JpaRepositoriesMarker;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * Configuration for setting up JPA and transaction management.
 */
@Configuration
@AllArgsConstructor
@EnableJpaRepositories(basePackageClasses = _JpaRepositoriesMarker.class)
@PropertySource({"file:/usr/local/tomcat/conf/database.properties"})
@EnableTransactionManagement
public class PersistenceJPAConfiguration {

    private final org.springframework.core.env.Environment environment;

    /**
     * Configures the entity manager factory bean.
     *
     * @return the configured entity manager factory bean.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(_EntityMarker.class.getPackageName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, environment.getProperty(Environment.DIALECT));
        properties.setProperty(Environment.HBM2DDL_AUTO, environment.getProperty(Environment.HBM2DDL_AUTO));
        properties.setProperty(Environment.SHOW_SQL, environment.getProperty(Environment.SHOW_SQL));
        properties.setProperty(Environment.FORMAT_SQL, environment.getProperty(Environment.FORMAT_SQL));
        return properties;
    }

    /**
     * Configures the transaction manager.
     *
     * @param entityManagerFactory the entity manager factory.
     * @return the configured transaction manager.
     */
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Configures the persistence exception translation post processor.
     *
     * @return the configured persistence exception translation post processor.
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Configures the data source.
     *
     * @return the configured data source.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.pass"));
        return dataSource;
    }
}
