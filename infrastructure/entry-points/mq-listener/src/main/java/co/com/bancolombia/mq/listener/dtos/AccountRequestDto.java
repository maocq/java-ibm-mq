package co.com.bancolombia.mq.listener.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.beanio.annotation.Field;
import org.beanio.annotation.Record;
import org.beanio.annotation.Segment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Record(name = "AccountRequestDto")
public class AccountRequestDto {

    @Segment(name = "header")
    private HeaderDto header;
    /*@Field(name = "header", length = 20)
    private String header;*/

    @Field(name = "name", length = 8)
    private String name;
    @Field(name = "balance", length = 10)
    private int balance;
}
