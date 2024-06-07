package co.com.bancolombia.mq.reqreply;

import co.com.bancolombia.commons.jms.mq.EnableMQGateway;
import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.account.gateways.AccountService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;

@Component
@AllArgsConstructor
@EnableMQGateway(scanBasePackages = "co.com.bancolombia")
public class SampleMQReqReply implements AccountService {

    private final ReqReplyGateway sender;

    @Override
    public Mono<Account> createAccount(Account account) {
        return sender.requestReply(account.name())
                .name("mq_req_reply")
                .tag("operation", "my-operation")
                .metrics()
                .map(message -> extractResponse(message, account));
    }

    @SneakyThrows
    private Account extractResponse(Message message, Account account) {
        TextMessage textMessage = (TextMessage) message;
        return account.toBuilder()
                .name(textMessage.getText()).build();
    }

    /*
    @SneakyThrows
    private String extractResponse(Message message) {
        TextMessage textMessage = (TextMessage) message;
        return textMessage.getText();
    }
     */
}
