package com.vikky.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombine {

  @Test
  public void combineWithMerge() {

    Flux<String> stringFlux = Flux.just("A", "B", "C", "D");
    Flux<String> stringFlux1 = Flux.just("D", "E", "F", "G");

    Flux<String> mergedFlux = Flux.merge(stringFlux, stringFlux1);

    StepVerifier.create(mergedFlux.log())
        .expectNext("A", "B", "C", "D", "D", "E", "F", "G")
        .verifyComplete();
  }

    @Test
    public void combineWithMergeDelay() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D").delayElements(Duration.ofSeconds(1));
        Flux<String> stringFlux1 = Flux.just("D", "E", "F", "G").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(stringFlux, stringFlux1);

        StepVerifier.create(mergedFlux.log())
                .expectNextCount(8)
                .verifyComplete();
    }


    //This is to have the Sequential flow of data in case of the delay
    @Test
    public void combineWithConcat() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D").delayElements(Duration.ofSeconds(1));
        Flux<String> stringFlux1 = Flux.just("D", "E", "F", "G").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(stringFlux,stringFlux1); //Concat(stringFlux, stringFlux1);

        StepVerifier.create(mergedFlux.log())
                .expectNext("A", "B", "C", "D", "D", "E", "F", "G")
                .verifyComplete();
    }
    @Test
    public void combineWithZip() {

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D").delayElements(Duration.ofSeconds(1));
        Flux<String> stringFlux1 = Flux.just("D", "E", "F", "G").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.zip(stringFlux,stringFlux1,(t1,t2)->{
            return t1.concat(t2);
        }); //concat(stringFlux,stringFlux1); //Concat(stringFlux, stringFlux1);

        StepVerifier.create(mergedFlux.log())
                .expectNext("AD", "BE", "CF", "DG")
                .verifyComplete();
    }
}

