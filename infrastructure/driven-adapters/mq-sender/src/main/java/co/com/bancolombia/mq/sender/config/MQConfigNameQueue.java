package co.com.bancolombia.mq.sender.config;

import co.com.bancolombia.commons.jms.api.MQQueueManagerSetter;
import co.com.bancolombia.commons.jms.api.MQQueuesContainer;
import co.com.bancolombia.commons.jms.mq.config.MQProperties;
import co.com.bancolombia.commons.jms.mq.utils.MQUtils;
import co.com.bancolombia.commons.jms.security.SSLContextUtils;
import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.mq.jakarta.jms.MQQueue;
import com.ibm.msg.client.jakarta.jms.JmsFactoryFactory;
import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static com.ibm.msg.client.jakarta.jms.JmsConstants.JAKARTA_WMQ_PROVIDER;
import static com.ibm.msg.client.jakarta.wmq.common.CommonConstants.*;

@Configuration
public class MQConfigNameQueue {

    @Bean
    public Destination destinationQueue(@Value("${commons.jms.input-queue}") String queue) throws JMSException {
        var mqQueue = new MQQueue(queue);
        MQUtils.setQMName(mqQueue, "QM1");
        return mqQueue;
    }

    @Bean
    public String inputQueue(@Value("${commons.jms.input-queue}") String queue) {
        return queue;
    }

    @Bean
    public MQQueueManagerSetter qmSetter(MQProperties properties, MQQueuesContainer container) {
        return (jmsContext, queue) -> {
            System.out.println("Self assigning Queue Manager to listening queue: {}" + queue.toString());
            MQUtils.setQMName(queue, "QM1");
            container.registerQueue(properties.getInputQueue(), queue);
        };
    }



    @Bean
    //@Profile("!local")
    public ConnectionFactory getMyConnectionFactory(/*MQConfigurationProperties properties*/)
            throws MalformedURLException, JMSException {

        URL ccdtUrl = new File("/Users/johncarm/mao/java/mq-connection/robert/aw1326001-ms-enriching-ccdt-cer-1.json").toURI().toURL();
        SSLSocketFactory sslSocketFactory = SSLContextUtils.buildSSLContextFromJks(
                        "/Users/johncarm/mao/java/mq-connection/robert/conexion_a_mq.jks", "12345678")
                .getSocketFactory();

        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(JAKARTA_WMQ_PROVIDER);
        MQConnectionFactory mqConnection = (MQConnectionFactory) ff.createConnectionFactory();
        mqConnection.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        mqConnection.setTransportType(WMQ_CM_CLIENT);
        mqConnection.setClientReconnectOptions(WMQ_CLIENT_RECONNECT);

        mqConnection.setStringProperty(WMQ_QUEUE_MANAGER, "QM1");
        mqConnection.setStringProperty(WMQ_CHANNEL, "DEV.APP.SVRCONN");

        mqConnection.setStringProperty(USERID, "app");
        mqConnection.setStringProperty(PASSWORD, "passw0rd");
        //mqConnection.setCCDTURL(ccdtUrl);
        //mqConnection.setSSLSocketFactory(sslSocketFactory);

        String tempModel = null; //"SYSTEM.DEFAULT.MODEL.QUEUE";
        if (tempModel != null) {
            mqConnection.setStringProperty(WMQ_TEMPORARY_MODEL, tempModel);
        }
        return mqConnection;
    }


    @Bean
    protected CommandLineRunner connect(ConnectionFactory connectionFactory) {

        return args -> {
            try (JMSContext context = connectionFactory.createContext()) {
                Destination destination = new MQQueue("DEV.QUEUE.1");
                JMSConsumer consumer = context.createConsumer(destination);

                consumer.setMessageListener(message -> {
                    try {
                        System.out.println(message.getJMSMessageID() + " "  + ((TextMessage) message).getText());
                    } catch (JMSException e) {
                        throw new RuntimeException(e);
                    }
                });
                context.setExceptionListener(Throwable::printStackTrace);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
    }

}
