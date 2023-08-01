package co.com.bancolombia.mq.sender;

import com.ibm.mq.jakarta.jms.MQQueue;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfigNameQueue {

    @Bean
    public Destination destinationQueue(@Value("${commons.jms.input-queue}") String queue) throws JMSException {
        return new MQQueue(queue);
    }
}