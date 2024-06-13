package co.com.bancolombia.mq.listener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beanio.annotation.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeaderDto {

    @Field(name = "prefix", length = 6)
    private String prefix;
    @Field(name = "info", length = 14)
    private String info;
}
