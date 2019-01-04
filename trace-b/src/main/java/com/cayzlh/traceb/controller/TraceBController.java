package com.cayzlh.traceb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author chenanyu
 * @Description trace-b
 * @date 2019-01-04.
 */
@RestController
public class TraceBController {

    @GetMapping("/trace-b")
    public Mono<String> trace() {
        System.out.println("call trace-b.");
        return Mono.just("Trace.");
    }

}
