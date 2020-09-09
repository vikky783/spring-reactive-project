package com.vikky.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.time.Duration;

public class ExceptionHandlingReactive {

    @Test
    public void testExceptionHandling(){
        Flux<String> stringFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new CustomException("Exception")))
                .concatWith(Flux.just("D"))
                .onErrorResume(e->{
                    System.out.println("Exception is " + e);
                            return Flux.just("Default");
                });
        StepVerifier.create(stringFlux.log())
                .expectNext("A","B","C")
                .expectNext("Default")
                //.expectError(CustomException.class)
                //.verify();
                .verifyComplete();

    }

    @Test
    public void testExceptionOnErrorReturn(){
        Flux<String> stringFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new CustomException("Exception")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("Default");


        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("Default")
                //.expectError(CustomException.class)
                //.verify();
                .verifyComplete();



    }
    @Test
    public void testExceptionRetryBackof(){
        Flux<String> stringFlux = Flux.just("A","B","C")
                .concatWith(Flux.error(new CustomException("Exception")))
                .concatWith(Flux.just("D"))
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(2)));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectNext("A","B","C")
                .expectError(IllegalStateException.class)
                //.expectError(CustomException.class)
                //.verify();
                .verify();

    }
}
