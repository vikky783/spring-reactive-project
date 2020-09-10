package com.vikky.reactive.repository;

import com.vikky.reactive.model.Items;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringReactiveRepo extends ReactiveMongoRepository<Items,String> {
}
