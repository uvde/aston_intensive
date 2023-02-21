package ru.urusov.astonmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {

    @GetMapping("/hello-world")
    public String sayHello(){
        return "hello_world";
    }
}
