package com.vikky.reactive.router;

import com.vikky.reactive.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.vikky.reactive.constant.Constants.ITEM_END_POINT_V1;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ItemRouter {

    @Bean
    public RouterFunction<ServerResponse> itemRoute(ItemHandler itemHandler){
        return RouterFunctions.route(GET(ITEM_END_POINT_V1)
                .and(accept(MediaType.APPLICATION_JSON))
                ,itemHandler::getServerResponse);
    }

}
