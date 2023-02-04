package com.niliusjulius.lolinfo.riot.lol.component;

import com.niliusjulius.lolinfo.riot.lol.validation.LeagueShardConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.impl.lol.builders.summoner.SummonerBuilder;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@RequiredArgsConstructor
public class LolSummoner {

    private final SummonerBuilder summonerBuilder;

    public Summoner retrieveSummoner(@LeagueShardConstraint String shardName, @NotBlank(message = "{summoner.error.name.not.empty}") String summonerName) {
        return summonerBuilder.withPlatform(LeagueShard.fromString(shardName).orElse(null)).withName(summonerName).get();
    }
}
