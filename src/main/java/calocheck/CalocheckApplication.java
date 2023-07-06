package calocheck;

import calocheck.base.util.FoodDataExtractor;
import calocheck.boundedContext.photo.config.GCPConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CalocheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalocheckApplication.class, args);
    }

}
