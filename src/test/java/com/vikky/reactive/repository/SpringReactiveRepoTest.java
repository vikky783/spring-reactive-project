package com.vikky.reactive.repository;

import com.vikky.reactive.model.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataMongoTest
//@RunWith(SpringRunner.class)
public class SpringReactiveRepoTest {

    @Autowired
   private SpringReactiveRepo springReactiveRepo;

    List<Items> itemsList = Arrays.asList(new Items(null,"Samsung TV",400.0),
    new Items(null,"Apple TV",420.0),
    new Items(null,"LG TV",4040.0),new Items(null,"Sony TV",450.0),
    new Items("ABC","BOSE",450.0));

    @BeforeEach
    public void  setup(){
        springReactiveRepo//.saveAll(Flux.fromIterable(itemsList))


                .deleteAll().thenMany(Flux.fromIterable(itemsList))
                .flatMap(springReactiveRepo::save)
                .doOnNext((item->{System.out.println("Item inserted is " +item);}))
                .blockLast();
    }

    @Test
    public void getAllTest(){
        StepVerifier.create(springReactiveRepo.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void getItemById(){
        StepVerifier.create(springReactiveRepo.findById("ABC"))
                .expectSubscription()
                .expectNextMatches((s->s.getItem().equals("BOSE")))
                .verifyComplete();
    }

    @Test
    public void findByItem(){
        StepVerifier.create(springReactiveRepo.findByItem("BOSE"))
                .expectSubscription()
                .expectNextMatches((s->s.getItem().equals("BOSE")))
                .verifyComplete();
    }

}
