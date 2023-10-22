package ru.gladun.historylearningplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = ReactiveElasticsearchRestClientAutoConfiguration.class)
@ConfigurationPropertiesScan
public class HistoryLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryLearningPlatformApplication.class, args);
    }

}
