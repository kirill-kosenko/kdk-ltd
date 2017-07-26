package kdk.ltd.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.inject.Inject;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


@Configuration
@EnableTransactionManagement(
        mode = AdviceMode.ASPECTJ
)
@EnableJpaRepositories(
        basePackages = "kdk.ltd.site.root.repositories",
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "jpaTransactionManager"
)
@ComponentScan(
        basePackages = "kdk.ltd.site.root"
)
@Import(SecurityConfig.class)
@PropertySource({ "classpath:persistence-${envTarget:mysql}.properties" })
public class RootContextConfiguration {

    @Inject
    AutowireCapableBeanFactory beanFactory;

    @Inject
    Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        return dataSource;
        /*JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource("jdbc/kdk_ltd");
        */
    }

   /* @Bean
    public DataSource h2DataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }                */

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

        HibernateJpaVendorAdapter adapter =
                new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(this.dataSource());
        factory.setPackagesToScan("kdk.ltd.site.root.entities");
        factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        factory.setValidationMode(ValidationMode.NONE);
        factory.setJpaProperties(additionalProperties());
        return factory;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

        hibernateProperties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.order_inserts", env.getProperty("hibernate.order_inserts"));
        hibernateProperties.setProperty("hibernate.order_updates", env.getProperty("hibernate.order_updates"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_versioned_data", env.getProperty("hibernate.jdbc.batch_versioned_data"));
        hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
        return hibernateProperties;
    }


    @Bean
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(
                this.entityManagerFactoryBean().getObject());
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() throws ClassNotFoundException {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() throws ClassNotFoundException {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
                false);
        return mapper;
    }

    @Bean(name = "currentDateTimePoint")
    public LocalDateTime currentDateTimePoint() {
        return LocalDateTime.of(2000, 1, 1, 0, 0);
    }
}
