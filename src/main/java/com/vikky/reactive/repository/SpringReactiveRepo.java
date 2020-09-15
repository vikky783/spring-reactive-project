package com.vikky.reactive.repository;

import com.vikky.reactive.model.Items;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SpringReactiveRepo extends ReactiveMongoRepository<Items,String> {

    Flux<Items> findByItem(String item);
}
