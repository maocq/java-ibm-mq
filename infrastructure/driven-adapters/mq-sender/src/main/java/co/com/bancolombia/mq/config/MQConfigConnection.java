package co.com.bancolombia.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "adapter.mq")
public class MQConfigConnection {
    private String user;
    private String ccdtUrl;
    private String jksUrl;
    private String jskPassword;
}