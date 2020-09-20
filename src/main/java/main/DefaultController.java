package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DefaultController {
    @RequestMapping("/")
    public String randomInt() {
        Random random = new Random();
        return Integer.toString(random.nextInt());
    }
}
