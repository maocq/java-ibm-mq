package co.com.bancolombia.mq.sender;

import co.com.bancolombia.commons.jms.api.MQMessageSelectorListener;
import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.mq.EnableMQMessageSender;
import co.com.bancolombia.commons.jms.mq.EnableMQSelectorMessageListener;
import co.com.bancolombia.model.services.ReqReplyServiceFixedQueue;
import jakarta.jms.Destination;
import jakarta.jms.TextMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;

@Component
@AllArgsConstructor
@EnableMQMessageSender
@EnableMQSelectorMessageListener // Enable it to retrieve a specific message by correlationId
public class SampleMQMessageSender implements ReqReplyServiceFixedQueue {
    private final MQMessageSender sender;
//    private final MQQueuesContainer container; // Inject it to reference a queue
    private final MQMessageSelectorListener listener; // Inject it to retrieve a specific message by correlationId

    private final Destination destinationQueue;

    public Mono<String> requestReply(String message) {
        return send(message)
                .flatMap(this::getResult);
    }

    private Mono<String> send(String message) {
        return sender.send(context -> {
            Message textMessage = context.createTextMessage(message);
            textMessage.setJMSReplyTo(destinationQueue);
            return textMessage;
        });
    }

    // Enable it to retrieve a specific message by correlationId
    private Mono<String> getResult(String correlationId) {
        return listener.getMessage(correlationId)
                .map(this::extractResponse);
    }

    @SneakyThrows
    private String extractResponse(Message message) {
        TextMessage textMessage = (TextMessage) message;
        return textMessage.getText();
    }
}
