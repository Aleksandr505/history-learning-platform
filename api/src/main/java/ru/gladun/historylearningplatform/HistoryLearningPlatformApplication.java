package ru.gladun.historylearningplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = ReactiveElasticsearchRestClientAutoConfiguration.class)
public class HistoryLearningPlatformApplication {

    // Перед началом работы запустите команду:
    // docker run -d --name ES -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
    public static void main(String[] args) {
        SpringApplication.run(HistoryLearningPlatformApplication.class, args);
    }

}
