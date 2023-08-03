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

    /*
    @Bean
    @Profile("!local")
    public ConnectionFactory getMyConnectionFactory(MQConfigurationProperties properties)
            throws MalformedURLException, JMSException {
        URL ccdtUrl = new File("/Users/johncarm/mao/java/mq-connection/d2b-mq-ccdt.json").toURI().toURL();
        SSLSocketFactory sslSocketFactory = SSLContextUtils.buildSSLContextFromJks(
                        "/Users/johncarm/mao/java/mq-connection/d2b-mq-credentials.jks", "axhNLiaGrT5SknK5")
                .getSocketFactory();

        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(JAKARTA_WMQ_PROVIDER);
        MQConnectionFactory mqConnection = (MQConnectionFactory) ff.createConnectionFactory();
        mqConnection.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        mqConnection.setTransportType(WMQ_CM_CLIENT);
        mqConnection.setClientReconnectOptions(WMQ_CLIENT_RECONNECT);

        mqConnection.setStringProperty(WMQ_QUEUE_MANAGER, "ALMSGRP");
        mqConnection.setStringProperty(USERID, "cnxqqalm");
        mqConnection.setCCDTURL(ccdtUrl);
        mqConnection.setSSLSocketFactory(sslSocketFactory);
        if (properties.getTempModel() != null) {
            mqConnection.setStringProperty(WMQ_TEMPORARY_MODEL, properties.getTempModel());
        }
        return mqConnection;
    }
     */
}