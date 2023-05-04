package app.sami.languageWeb.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Value("${hello}")
    private String hello;
    @Value("${bye.value}")
    private String bye;
    public HelloDTO hello(){

        return HelloDTO.builder().hello(this.hello).bye(this.bye).build();
    }
}
