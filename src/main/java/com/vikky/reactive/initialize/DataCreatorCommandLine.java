package com.vikky.reactive.initialize;

import com.vikky.reactive.model.Items;
import com.vikky.reactive.repository.SpringReactiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class DataCreatorCommandLine implements CommandLineRunner {
    @Autowired
    SpringReactiveRepo springReactiveRepo;

    public List<Items> addItems(){
        return Arrays.asList(new Items(null,"Airtel TV ",100.0),
                new Items(null,"SUN TV ",200.0),
                new Items(null,"SKY TV ",300.0),
                new Items(null,"JIO TV ",400.0));
    }

    public void addToDataBase(){
        springReactiveRepo.deleteAll()
                .thenMany(Flux.fromIterable(addItems()).log())
                .flatMap(springReactiveRepo::save)
                .thenMany(springReactiveRepo.findAll())
                .subscribe((items -> {System.out.println("The item inserted is "+items);
                                }));
    }


    @Override
    public void run(String... args) throws Exception {
        addToDataBase();
    }
}
