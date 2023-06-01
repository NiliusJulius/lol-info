package com.niliusjulius.lolinfo.riot.lol.bean;

import no.stelar7.api.r4j.impl.lol.builders.championmastery.ChampionMasteryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChampionMasteryBuilderBean {

    @Bean
    public ChampionMasteryBuilder championMasteryBuilder() {
        return new ChampionMasteryBuilder();
    }
}
