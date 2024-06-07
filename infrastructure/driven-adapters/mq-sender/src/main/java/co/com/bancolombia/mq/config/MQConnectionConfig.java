package co.com.bancolombia.mq.config;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.msg.client.jakarta.jms.JmsFactoryFactory;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;

import static com.ibm.msg.client.jakarta.jms.JmsConstants.JAKARTA_WMQ_PROVIDER;
import static com.ibm.msg.client.jakarta.wmq.common.CommonConstants.*;

@Configuration
@EnableConfigurationProperties({MQConfigurationProperties.class})
public class MQConnectionConfig {

    @Bean
    public ConnectionFactory getMyConnectionFactory(MQConfigurationProperties mqProperties, MQConfigConnection properties) throws JMSException, MalformedURLException {
        /*URL ccdtUrl = new File(properties.getCcdtUrl()).toURI().toURL();
        SSLSocketFactory sslSocketFactory = SSLContextUtils.buildSSLContextFromJks(
                properties.getJksUrl(), properties.getJskPassword()).getSocketFactory();*/

        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");

        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(JAKARTA_WMQ_PROVIDER);
        MQConnectionFactory mqConnection = (MQConnectionFactory) ff.createConnectionFactory();
        mqConnection.setIntProperty(WMQ_CONNECTION_MODE, WMQ_CM_CLIENT);
        mqConnection.setTransportType(WMQ_CM_CLIENT);
        mqConnection.setClientReconnectOptions(WMQ_CLIENT_RECONNECT);

        mqConnection.setStringProperty(WMQ_QUEUE_MANAGER, mqProperties.getQueueManager()); //QM1
        mqConnection.setStringProperty(WMQ_CHANNEL, mqProperties.getChannel()); //DEV.APP.SVRCONN
        mqConnection.setStringProperty(USERID, mqProperties.getUser());
        mqConnection.setStringProperty(PASSWORD, mqProperties.getPassword());

        /*mqConnection.setCCDTURL(ccdtUrl);
        mqConnection.setSSLSocketFactory(sslSocketFactory);*/
        return mqConnection;
    }
}
