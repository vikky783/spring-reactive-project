package com.vikky.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FactoryMethods {

  List<String> stringList = Arrays.asList("New", "World", "Hello", "Worlds");

  @Test
  public void fluxUsingIterable() {
    Flux<String> stringFlux = Flux.fromIterable(stringList);

    StepVerifier.create(stringFlux).expectNext("New", "World", "Hello", "Worlds").expectComplete();
  }

  public void fluxUsingArray() {
    String[] strings = new String[] {"New", "World", "Hello", "Worlds"};
    Flux<String> stringFlux = Flux.fromArray(strings);

    StepVerifier.create(stringFlux).expectNext("New", "World", "Hello", "Worlds").expectComplete();
  }

  @Test
  public void fluxUsingStream() {
    Flux<String> stringFlux = Flux.fromStream(stringList.stream());
    StepVerifier.create(stringFlux.log())
        .expectNext("New", "World", "Hello", "Worlds")
        .expectComplete();
  }

  @Test
  public void monoUsingJustOrEmpty(){
    Mono<String> stringMono = Mono.justOrEmpty("Hello");

    StepVerifier.create(stringMono.log())
            .expectNext("Hello")
            .expectComplete();
  }

  @Test
  public void monoUsingSupplier(){
    Supplier<String> stringSupplier = () -> "Adam";
    Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
    StepVerifier.create(stringMono)
            .expectNext("Adam")
            .expectComplete();
  }

  @Test
  public void monoUsingRange(){
    Flux<Integer> integerFlux = Flux.range(1,5);

    StepVerifier.create(integerFlux).expectNext(1,2,3,4,5).expectComplete();
  }
}
