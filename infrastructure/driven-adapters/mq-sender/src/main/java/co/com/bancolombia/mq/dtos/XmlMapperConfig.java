package co.com.bancolombia.mq.dtos;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION;

//@Configuration
public class XmlMapperConfig {

    /**
     * Para evitar coflictos con el ObjectMapper de Spring se recomienda instanciar el XmlMapper en el constructor
     * de la clase que lo necesita usar o hacer uso de un wrapper. Ver (SampleMQReqReply.java)
     */
    //@Bean
    public XmlMapper xmlMapper() {
        var xmlMapper = new XmlMapper();
        xmlMapper.configure(WRITE_XML_DECLARATION, false);
        //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper;
    }
}
