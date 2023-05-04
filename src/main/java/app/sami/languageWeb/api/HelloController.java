package app.sami.languageWeb.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HelloController {

    private HelloService helloService;

    @GetMapping("/hello")
    public HelloDTO helloWorld(){

        return helloService.hello();
    }

}
