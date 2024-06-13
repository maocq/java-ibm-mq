package co.com.bancolombia.mq.listener;

import co.com.bancolombia.commons.jms.mq.MQListener;
import co.com.bancolombia.mq.listener.dtos.AccountRequestDto;
import co.com.bancolombia.usecase.createaccount.CreateAccountUseCase;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.beanio.Marshaller;
import org.beanio.StreamFactory;
import org.beanio.Unmarshaller;
import org.beanio.builder.StreamBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SampleMQMessageListener {
    private final Timer timer = Metrics.timer("mq_receive_message", "operation", "my-operation");

    private final CreateAccountUseCase createAccountUseCase;
    private final Marshaller accountMarshaller;
    private final Unmarshaller accountUnmarshaller;

    public SampleMQMessageListener(CreateAccountUseCase createAccountUseCase) {
        var streamFactory = StreamFactory.newInstance();
        streamFactory.define(new StreamBuilder("accountRequestDto").format("fixedlength")
                .addRecord(AccountRequestDto.class));

        this.accountMarshaller = streamFactory.createMarshaller("accountRequestDto");
        this.accountUnmarshaller = streamFactory.createUnmarshaller("accountRequestDto");
        this.createAccountUseCase = createAccountUseCase;
    }

    @MQListener("${commons.jms.input-queue}")
    public Mono<Void> process(Message message) throws JMSException {
        timer.record(System.currentTimeMillis() - message.getJMSTimestamp(), TimeUnit.MILLISECONDS);
        String text = ((TextMessage) message).getText();

        var dto = (AccountRequestDto) accountUnmarshaller.unmarshal(text);
        //String plainData = accountMarshaller.marshal(dto).toString();
        log.info("Received message: {}", dto);
        return createAccountUseCase.create(dto.getName()).then();
    }
}
