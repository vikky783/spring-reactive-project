package com.vikky.reactive.handler;

import com.vikky.reactive.model.Items;
import com.vikky.reactive.repository.SpringReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ItemHandler {
    @Autowired
    SpringReactiveRepo springReactiveRepo;

    public Mono<ServerResponse> getServerResponse(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(springReactiveRepo.findAll(), Items.class);
    }

}
