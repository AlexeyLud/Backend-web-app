package come.alex.grow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"come.alex.grow.services", "come.alex.grow.controllers",
        "come.alex.grow.serviceImpl", "come.alex.grow.configs"})
@EntityScan(basePackages = "come.alex.grow.entity")
@EnableJpaRepositories(basePackages = "come.alex.grow.repositories")
public class LabaApplication {

    private static final Logger logger = LoggerFactory.getLogger(LabaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LabaApplication.class, args);
	}

}
