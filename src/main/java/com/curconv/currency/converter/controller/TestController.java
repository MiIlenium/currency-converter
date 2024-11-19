package com.curconv.currency.converter.controller;
import com.curconv.currency.converter.service.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final Test testing;

    @GetMapping("/api/getSomething")
    public Object sendRequest(){
        return testing.sendRequest();
    }
}
