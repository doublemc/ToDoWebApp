package configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by michal on 29.01.17.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.doublemc.domain"})
@EnableJpaRepositories(basePackages = {"com.doublemc.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
