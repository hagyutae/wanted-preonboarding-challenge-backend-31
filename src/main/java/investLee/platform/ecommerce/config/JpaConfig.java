package investLee.platform.ecommerce.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
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
@EnableJpaRepositories(
        basePackages = "investLee.platform.ecommerce.domain",
        entityManagerFactoryRef = "wantedEntityManagerFactory",
        transactionManagerRef = "wantedTransactionManager"
)
@EnableTransactionManagement
public class JpaConfig {

    @Bean("wantedDataSource")
    @ConfigurationProperties(prefix = "spring.jpa.wanted-db.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("wantedJpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa.wanted-db.properties")
    public Properties jpaProperties() {
        return new Properties();
    }

    @Bean("wantedEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean wantedEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("wantedDataSource") DataSource dataSource,
            @Qualifier("wantedJpaProperties") Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factoryBean = builder
                .dataSource(dataSource)
                .packages("investLee.platform.ecommerce.domain")
                .build();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties);
        return factoryBean;
    }

    @Bean("wantedTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("wantedEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

