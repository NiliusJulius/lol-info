package com.niliusjulius.lolinfo.riot.lol.bean;

import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SummonerBuilderBean {

    @Bean public SummonerBuilder summonerBuilder() {
        return new SummonerBuilder();
    }
}
