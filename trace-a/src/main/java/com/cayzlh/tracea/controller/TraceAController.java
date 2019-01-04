package com.cayzlh.tracea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author chenanyu
 * @Description trace-a
 * @date 2019-01-04.
 */
@RestController
public class TraceAController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/trace-a")
    public Mono<String> trace() {
        System.out.println("call trace-a.");

        return webClient.get().uri("/trace-b").retrieve().bodyToMono(String.class);

    }

}
