package com.intuit.sambokar.bucket;
import com.intuit.sambokar.bucket.service.AmazonClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class AmazonClientTestConfiguration {
    @Bean
    @Primary
    public AmazonClient awsClient() {
        return Mockito.mock(AmazonClient.class);
    }
}
