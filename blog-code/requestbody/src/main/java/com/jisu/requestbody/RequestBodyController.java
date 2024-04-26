package com.jisu.requestbody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class RequestBodyController {

    @PostMapping
    public RequestBodyDto getTime(@RequestBody RequestBodyDto requestBodyDto) {
        System.out.println("RequestBodyController = " + requestBodyDto);
        return requestBodyDto;
    }
}
