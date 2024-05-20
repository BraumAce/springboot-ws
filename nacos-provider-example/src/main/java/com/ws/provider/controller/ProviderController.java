package com.ws.provider.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/get")
public class ProviderController {

    @GetMapping("/{string}")
    public String get(@PathVariable String string){
        return "这是 " + string;
    }

}
