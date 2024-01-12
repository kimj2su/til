package com.example.redis;

import com.example.redis.event.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final MemberService memberService;


    @GetMapping("/")
    public String test() {
        memberService.temp();
        return "ok";
    }
}
