# Java IBM MQ

- Get an IBM MQ queue for development in a container
https://developer.ibm.com/learningpaths/ibm-mq-badge/create-configure-queue-manager/

- Mac Arm64
```sh
git clone https://github.com/ibm-messaging/mq-container.git
cd mq-container
make build-devserver
#image => ibm-mqadvanced-server-dev
```

```sh
docker exec -ti ibmmq bash
dspmqver
```

https://localhost:9443/ibmmq
admin/pass


---

```java
@Configuration
public class DestionationsConfig {

    @Bean
    public Destination outputQueue(@Value("${commons.jms.output-queue}") String name) throws JMSException {
        return new MQQueue("DEV.QUEUE.4");
    }

    @Bean
    public Destination inputQueue(@Value("${commons.jms.input-queue}") String name) throws JMSException {
        var destination = new MQQueue("DEV.QUEUE.5");
        //destination.setTargetClient(WMQ_CLIENT_NONJMS_MQ);
        //destination.setPriority(LOW_PRIORITY);
        //destination.setPersistence(NON_PERSISTENT);
        //MQUtils.setQMName(destination, "QM1");
        return destination;
    }
}
```

```java
@ReqReply(requestQueue = "#{outputQueue}", replyQueue = "#{inputQueue}", queueType = FIXED)
public interface OtherReqReplyGateway  extends MQRequestReply { }
```