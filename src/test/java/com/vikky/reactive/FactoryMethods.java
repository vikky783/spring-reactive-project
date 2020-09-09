package com.vikky.reactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static reactor.core.scheduler.Schedulers.parallel;

public class FactoryMethods {

  List<String> stringList = Arrays.asList("New", "World", "Hello", "Worlds");

  @Test
  public void fluxUsingIterable() {
    Flux<String> stringFlux = Flux.fromIterable(stringList);

    StepVerifier.create(stringFlux)
            .expectNext("New", "World", "Hello", "Worlds")
            .expectComplete();
  }

  public void fluxUsingArray() {
    String[] strings = new String[] {"New", "World", "Hello", "Worlds"};
    Flux<String> stringFlux = Flux.fromArray(strings);

    StepVerifier.create(stringFlux)
            .expectNext("New", "World", "Hello", "Worlds")
            .expectComplete().verify();
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

    StepVerifier.create(integerFlux)
            .expectNext(1,2,3,4,5)
            .expectComplete();
  }

  @Test
  public void fluxFilter(){
    Flux<String> stringFlux = Flux.fromIterable(stringList)
            .filter((i->i.startsWith("W"))).repeat(0);

    StepVerifier.create(stringFlux)
            .expectNext("World", "Worlds")
            .verifyComplete();
  }

  @Test
  public void fluxWithFlatMap() {
    Flux<String> stringFlux =
        Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F", "G"))
            .flatMap(
                (p) -> {
                  return Flux.fromIterable(convertToList(p));
                })
            .log();

    StepVerifier.create(stringFlux)
            .expectNextCount(14)
            .verifyComplete();
  }

  private List<String> convertToList(String p) {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(p,"Appended value");
  }

  @Test
  public void parallelFlux() {
    Flux<String> stringFlux =
        Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F", "G"))
            .window(3)
            .flatMap((s) -> s.map(this::convertToList)
                    .subscribeOn(parallel()))
            .flatMap(s -> Flux.fromIterable(s))
            .log();

    StepVerifier.create(stringFlux).expectNextCount(14).verifyComplete();
  }

  @Test
  public void parallelFluxInSequence(){
    Flux<String> stringFlux =
            Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                    .window(2)
                    .flatMapSequential((s)->s.map(this::convertToList)
                    .subscribeOn(parallel()))
                    .flatMap(s->Flux.fromIterable(s))
                    .log();

    StepVerifier.create(stringFlux)
            .expectNextCount(12)
            .verifyComplete();
  }
}
