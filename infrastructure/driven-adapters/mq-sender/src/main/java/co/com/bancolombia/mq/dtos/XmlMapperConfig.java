package co.com.bancolombia.mq.dtos;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator.Feature.WRITE_XML_DECLARATION;

@Configuration
public class XmlMapperConfig {

    @Bean
    public XmlMapper xmlMapper() {
        var xmlMapper = new XmlMapper();
        xmlMapper.configure(WRITE_XML_DECLARATION, false);
        //xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper;
    }
}
