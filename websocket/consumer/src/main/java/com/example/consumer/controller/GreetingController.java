package com.example.consumer.controller;

import com.example.consumer.message.Greeting;
import com.example.consumer.message.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    /**
     * 메세지가 /hello로 전송되는 경우 greeting() 메소드가 실행된다.
     * 이 메소드는 @SendTo 어노테이션에 의해 /topic/greetings 를 구독하고 있는 구독자에게 브로드캐스트된다.
     * @SendTo 어노테이션은 @MessageMapping 어노테이션과 함께 사용되어야 한다.
     * 입력 메시지는 삭제 된다.
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println("message = " + message.getName());
//        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}