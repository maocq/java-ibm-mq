package co.com.bancolombia.mq.reqreply;

import co.com.bancolombia.commons.jms.mq.EnableMQGateway;
import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.account.gateways.AccountService;
import co.com.bancolombia.mq.dtos.AccountXmlDto;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;

import static com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION;

@Component
@EnableMQGateway(scanBasePackages = "co.com.bancolombia")
public class SampleMQReqReply implements AccountService {

    private final ReqReplyGateway sender;
    private final XmlMapper xmlMapper;

    public SampleMQReqReply(ReqReplyGateway sender) {
        var xmlMapper = new XmlMapper();
        xmlMapper.configure(WRITE_XML_DECLARATION, false);
        //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        this.sender = sender;
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Mono<Account> createAccount(Account account) {

        var xmlDto = generateXmlRequest(account);
        return sender.requestReply(xmlDto)
                .name("mq_req_reply")
                .tag("operation", "my-operation")
                .metrics()
                .map(message -> extractResponse(message, account));
    }

    @SneakyThrows
    private String generateXmlRequest(Account account) {
        AccountXmlDto dto = new AccountXmlDto(account.name(), account.balance());
        return xmlMapper.writeValueAsString(dto);
    }

    @SneakyThrows
    private Account extractResponse(Message message, Account account) {
        TextMessage textMessage = (TextMessage) message;
        var xmlDto = textMessage.getText();

        AccountXmlDto dto = xmlMapper.readValue(xmlDto, AccountXmlDto.class);
        return account.toBuilder()
                .name(dto.getName()).build();
    }
}
