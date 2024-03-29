package ru.gladun.historylearningplatform.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import ru.gladun.historylearningplatform.repository.EsArticleRepository;

@Configuration
@EnableElasticsearchRepositories(basePackageClasses = {EsArticleRepository.class})
public class EsConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    public String elasticsearchUrl;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration config
                = ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .withBasicAuth(username, password)
                .build();

        return RestClients.create(config).rest();
    }

}
