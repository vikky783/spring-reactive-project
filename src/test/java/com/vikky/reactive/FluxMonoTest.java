package com.vikky.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoTest {

  @Test
  public void fluxTest() {
    Flux<String> stringFlux =
        Flux.just("Spring boot", "Spring Data", "Spring Security")
           // .concatWith(Flux.error(new RuntimeException("I dont know but something went wrong")))
            .log();

    stringFlux.subscribe(i -> System.out.println(i), e -> System.err.println(e));
  }

  @Test
  public void testFluxWithError() {
    Flux<String> stringFlux =
        Flux.just("Spring boot", "Spring Data", "Spring Security")
           // .concatWith(Flux.error(new RuntimeException("I dont know but something went wrong")))
            .log();

    StepVerifier.create(stringFlux)
        .expectNext("Spring boot")
        .expectNext("Spring Data")
        .expectNext("Spring Security")
            .expectError(RuntimeException.class);
        //.expectErrorMessage("I dont know but something went rong");
    // .verifyComplete();
  }

}