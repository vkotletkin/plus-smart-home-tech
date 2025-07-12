package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.practicum.service.AggregationStarter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AggregationApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(AggregationApplication.class, args);

        AggregationStarter starter = context.getBean(AggregationStarter.class);
        starter.start();
    }
}
